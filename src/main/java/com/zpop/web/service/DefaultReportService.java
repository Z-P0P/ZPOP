package com.zpop.web.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zpop.web.dao.ReportedCommentDao;
import com.zpop.web.dao.ReportedMeetingDao;
import com.zpop.web.dao.ReportedMemberDao;
import com.zpop.web.entity.ReportedComment;
import com.zpop.web.entity.ReportedMeeting;
import com.zpop.web.entity.ReportedMember;

@Service
public class DefaultReportService implements ReportService {
	
	@Autowired
	private ReportedMeetingDao reportedMeetingDao;
	
	@Autowired
	private ReportedMemberDao reportedMemberDao;
	
	@Autowired
	private ReportedCommentDao reportedCommentDao;
		
	public DefaultReportService() {};

	public DefaultReportService(
			ReportedMeetingDao reportedMeetingDao, 
			ReportedMemberDao reportedMemberDao,
			ReportedCommentDao reportedCommentDao
			) {
		
		this.reportedMeetingDao = reportedMeetingDao;
		this.reportedMemberDao = reportedMemberDao;
		this.reportedCommentDao = reportedCommentDao;
		
	}
	
	@Override
	public void createMeetingReport(ReportedMeeting reportedMeeting) {
		
		reportedMeetingDao.insert(reportedMeeting);
		
	}

	@Override
	public void createMemberReport(ReportedMember reportedMember) {

		reportedMemberDao.insert(reportedMember);
		
	}

	@Override
	public boolean createCommentReport(ReportedComment reportedComment) {
		
		reportedCommentDao.insert(reportedComment);
		
		return true;
	}

	@Override
	public int[] getReportedCommentId(int commentId, int reporterId) {

		int[] result = reportedCommentDao.select(commentId, reporterId);
    
		return result;
	}

	@Override
	public int[] getReportedMeetingId(int meetingId, int reporterId) {
		
		int[] list = reportedMeetingDao.getReportedMeetingId(meetingId, reporterId);
		
		return list;
	}
}