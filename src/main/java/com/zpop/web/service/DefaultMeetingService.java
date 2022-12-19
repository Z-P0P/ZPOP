package com.zpop.web.service;

import com.zpop.web.dao.MeetingDao;
import com.zpop.web.dao.ParticipationDao;
import com.zpop.web.dto.MeetingThumbnailPagination;
import com.zpop.web.dto.MeetingThumbnailResponse;
import com.zpop.web.entity.Member;
import com.zpop.web.entity.Participation;
import com.zpop.web.entity.meeting.Meeting;
import com.zpop.web.entity.meeting.MeetingThumbnailView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DefaultMeetingService implements MeetingService{

    @Autowired
    private MeetingDao dao;
    @Autowired
    private ParticipationDao participationDao;

    public DefaultMeetingService() {
    }

    public DefaultMeetingService(MeetingDao dao, ParticipationDao participationDao){
        this.dao = dao;
        this.participationDao = participationDao;
    }

    @Override
    public List<MeetingThumbnailResponse> getList(int startId, String keyword, Boolean isClosed) {
        // 페이징옵션이 없을 때 ex:모임 리스트 조회 첫 화면
        MeetingThumbnailPagination pagination = new MeetingThumbnailPagination(startId, keyword, isClosed);
        List<MeetingThumbnailView> meetingThumbnailViews = dao.getThumbnailViewList(pagination);

        // 응답에 맞게 데이터 변환
        List<MeetingThumbnailResponse> list = new ArrayList<>();
        for(MeetingThumbnailView m : meetingThumbnailViews) {
            list.add(MeetingThumbnailResponse.of(m));
        }

        return list;
    }

    @Override
    public boolean delete(int id, Member member) {

        Meeting foundMeeting = dao.get(id);

        if(foundMeeting == null || foundMeeting.getDeletedAt() != null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 모임입니다");

        int memberId = member.getId();

        if(foundMeeting.getRegMemberId() != memberId)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "권한이 없습니다");

        List<Participation> participations = participationDao.getListByMeetingId(id);

        for(Participation p : participations) {
            // 주최자 자기 자신 제외
            if(p.getParticipantId() == memberId)
                continue;

            Date bannedAt = p.getBannedAt();
            Date canceledAt = p.getCanceledAt();

            // 정상 참가자가 한명이라도 있으면 삭제 불가
            if(bannedAt == null && canceledAt == null )
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "참가자가 있어 모임을 삭제할 수 없습니다");
        }

        dao.updateDeletedAt(foundMeeting);

        return true;
    }
}
