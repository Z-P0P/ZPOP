package com.zpop.web.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zpop.web.dao.ReportedMeetingDao;
import com.zpop.web.entity.ReportedMeeting;

@Service
public class DefaultReportedMeetingService implements ReportService {
	
	@Autowired
	private ReportedMeetingDao reportedMeetingDao;
		
	public DefaultReportedMeetingService() {}

	public DefaultReportedMeetingService(ReportedMeetingDao reportedMeetingDao) {
		
		this.reportedMeetingDao = reportedMeetingDao;
		
	}
	
	@Override
	public void createMeetingReport(int typeId, String reason) {
		
		Date today = new Date();
	
		ReportedMeeting meetingReport = new ReportedMeeting(
				1, // id
				1, // meetingId
				1, // reportedId
				typeId, // typeId
				1, // adminId
				reason, // reason
				"아아", // original
				"아", // field
				today, // createdAt
				new Date(), // processedAt
				new Date(), // blockedAt
				new Date()); // releasedAt
		
		System.out.println(typeId);
		System.out.println(reason);
		System.out.println(reportedMeetingDao.insert(meetingReport));
	}

}