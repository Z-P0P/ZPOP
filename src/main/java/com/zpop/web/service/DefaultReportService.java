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
	public void createMeetingReport(int typeId, String reason) {
		
		Date today = new Date();
	
		ReportedMeeting meetingReport = new ReportedMeeting(
				
				1, // meetingId
				1, // reporterId
				typeId, // typeId
				1, // adminId
				reason, // reason
				"용인 축구팸 모집합니다. 실력은 좋아야 해요.", // original
				"축구팸 모집합니다.", // field
				today, // createdAt
				null, // processedAt
				null, // blockedAt
				null // releasedAt
				);
		
		// 일단 사용자 입력 받는 typeId 와 reason만 받아서 보내는걸로 해보았습니다
		
		System.out.println(typeId);
		System.out.println(reason);
		reportedMeetingDao.insert(meetingReport);
	}

	@Override
	public void createMemberReport(int typeId, String reason) {

		Date today = new Date();
		
		ReportedMember memberReport = new ReportedMember(
				1, 
				1, 
				typeId, 
				1, 
				reason, 
				today, 
				null,
				null,
				null
				);
		
		System.out.println(typeId);
		System.out.println(reason);
		reportedMemberDao.insert(memberReport);
		
	}

	@Override
	public void createCommentReport(int typeId, String reason) {

		Date today = new Date();
		
		ReportedComment commentReport = new ReportedComment(
				1, 
				1, 
				1, 
				typeId, 
				reason, 
				"original content", 
				today, 
				null,
				null,
				null
				);
		
		System.out.println(typeId);
		System.out.println(reason);
		reportedCommentDao.insert(commentReport);
		
	}
	
	

}