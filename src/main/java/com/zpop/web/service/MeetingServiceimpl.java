package com.zpop.web.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zpop.web.dao.MeetingDao;
import com.zpop.web.dao.ParticipationDao;
import com.zpop.web.entity.Meeting;
import com.zpop.web.entity.Participation;

@Service
public class MeetingServiceimpl implements MeetingService {
	
	@Autowired
	private MeetingDao meetingDao;
	
	@Autowired
	private ParticipationDao participationDao;
	
	
	@Override
	public int register(Meeting meeting) {
			
		return meetingDao.insert(meeting);
	}

	public boolean participate(int meetingId, int memberId) {
	
		// 주최자가 참여한 경우 -> host ID랑 MemberId랑 같을 경우
		// 참여하기를 눌렀는데 모임의 아이디가 없을 경우
		// 강퇴당한 사용자일 경우
		// 마감된 모임일 경우
		
		
		Participation participation = new Participation(meetingId,memberId );
		
		
		return participationDao.insert(participation);
	}

}
	
		
		
		
		
//	}
