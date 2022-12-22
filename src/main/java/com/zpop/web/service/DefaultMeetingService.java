package com.zpop.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zpop.web.dao.CategoryDao;
import com.zpop.web.dao.MeetingDao;
import com.zpop.web.dao.ParticipationDao;
import com.zpop.web.dto.MeetingThumbnailPagination;
import com.zpop.web.dto.MeetingThumbnailResponse;
import com.zpop.web.entity.Participation;
import com.zpop.web.entity.meeting.Meeting;
import com.zpop.web.entity.meeting.MeetingThumbnailView;

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

    public DefaultMeetingService(MeetingDao dao){
//        this.dao = dao;
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
}
