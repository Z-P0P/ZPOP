package com.zpop.web.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zpop.web.dao.MeetingDao;
import com.zpop.web.dto.admin.AdminMeetingDto;
import com.zpop.web.entity.meeting.Meeting;


@Service
public class DefaultAdminMeetingService implements AdminMeetingService {
	
	private final MeetingDao meetingDao;
	
	
	@Autowired
	public DefaultAdminMeetingService(MeetingDao meetingDao) {
		this.meetingDao = meetingDao;
	}


	@Override
	public List<AdminMeetingDto> getList(int page, String keyword, String option) {
		// TODO Auto-generated method stub
		int size = 10;
		int offset=(page-1)*size;
		List<AdminMeetingDto> list = meetingDao.getAdminViewList(size, offset, keyword, option);
		
		return list;
	}

	@Override
	public int count(String keyword, String option) {
		
		int count = meetingDao.count(keyword, option);
		
		return count;
	}



}
