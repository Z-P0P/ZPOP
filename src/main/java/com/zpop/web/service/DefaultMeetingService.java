package com.zpop.web.service;

import com.zpop.web.dao.MeetingDao;
import com.zpop.web.dao.ParticipationDao;
import com.zpop.web.dto.MeetingThumbnailPagination;
import com.zpop.web.dto.MeetingThumbnailResponse;
import com.zpop.web.entity.Member;
import com.zpop.web.entity.Participation;
import com.zpop.web.entity.meeting.Meeting;
import com.zpop.web.entity.meeting.MeetingThumbnailView;
import com.zpop.web.utils.TextDateTimeCalculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.zpop.web.dao.CategoryDao;

import com.zpop.web.dto.MeetingDetailDto;
import com.zpop.web.dto.MeetingParticipantsDto;


@Service
public class DefaultMeetingService implements MeetingService{

    @Autowired
    private MeetingDao dao;

    
    @Autowired
	private ParticipationDao participationDao;
    
    @Autowired
    private CategoryDao categoryDao;
    
    public DefaultMeetingService() {
    }

    public DefaultMeetingService(MeetingDao dao, ParticipationDao participationDao){
        this.dao = dao;
        this.participationDao = participationDao;

    }

    @Override
    public List<MeetingThumbnailResponse> getList() {
        return getList(
            0,
            null,
            null,
            null,
            false
        );
    }

    @Override
    public List<MeetingThumbnailResponse> getList(String keyword) {
        return getList(
            0,
            keyword,
            null,
            null,
            false
        );
    }

    @Override
    public List<MeetingThumbnailResponse> getList(
            int startId, String keyword, Integer categoryId, String strRegionIds, Boolean isClosed
            ) {
        String[] regionIds = null;
        if(strRegionIds != null)
            regionIds = strRegionIds.split(",");

        MeetingThumbnailPagination pagination = 
            new MeetingThumbnailPagination(startId, keyword, categoryId, regionIds, isClosed);

        List<MeetingThumbnailView> meetingThumbnailViews = dao.getThumbnailViewList(pagination);
        
        // 응답에 맞게 데이터 변환
        List<MeetingThumbnailResponse> list = new ArrayList<>();
        for(MeetingThumbnailView m : meetingThumbnailViews) {
            String genderCategory = "누구나";
            switch (m.getGenderCategory()) {
                case 1:
                    genderCategory = "남자 모임";
                    break;
                case 2:
                    genderCategory = "여자 모임";
                    break;
            }

            String dateTime = TextDateTimeCalculator.getTextDateTime(m.getStartedAt());

            boolean isClosedResult = false;
            if(m.getClosedAt() != null) 
                isClosedResult = true;
            
            MeetingThumbnailResponse meetingThumbnail = new MeetingThumbnailResponse(
                m.getId(),
                m.getCategory(),
                m.getRegion(),
                m.getAgeRange(),
                genderCategory,
                m.getMaxMember(),
                m.getTitle(),
                dateTime,
                m.getViewCount(),
                m.getCommentCount(),
                isClosedResult
            );

            list.add(meetingThumbnail);
        }

        return list;
    }

    
    
	@Override
	public int register(Meeting meeting) {
		return dao.insert(meeting);
	}

	public int participate(Participation participation) {
		// 주최자가 참여한 경우 -> host ID랑 MemberId랑 같을 경우
		// 참여하기를 눌렀는데 모임의 아이디가 없을 경우
		// 강퇴당한 사용자일 경우
		// 마감된 모임일 경우
//		Participation participation = new Participation(participation);
		return participationDao.insert(participation);
	}

	@Override
	public MeetingDetailDto getById(int id) {
		return dao.getDetailView(id);
	}

	@Override
	public List<MeetingParticipantsDto> getParticipants(int meetingId) {
		
		return participationDao.getByMeetingId(meetingId);
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
	@Override
	public void updateViewCount(int id) {
		dao.updateViewCount(id);
		
	}

    @Override
    public boolean close(int id, Member member) {

        Meeting foundMeeting = dao.get(id);

        if(foundMeeting == null || foundMeeting.getDeletedAt() != null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 모임입니다");
        
        int memberId = member.getId();

        if(foundMeeting.getRegMemberId() != memberId)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "권한이 없습니다");
        
        if(foundMeeting.getClosedAt() != null)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 마감된 모임입니다");
        
        // 모임 시작일이 지났을 때 -> 마감 후 예외
        Date startedAt = foundMeeting.getStartedAt();
        if(startedAt.before(new Date())) {
            dao.updateClosedAt(foundMeeting);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 마감된 모임입니다");
        }

        dao.updateClosedAt(foundMeeting);

        return true;
    }
}


