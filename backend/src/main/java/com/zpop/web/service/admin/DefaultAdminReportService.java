package com.zpop.web.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zpop.web.dao.ReportedCommentDao;
import com.zpop.web.dao.ReportedMeetingDao;
import com.zpop.web.dao.ReportedMemberDao;
import com.zpop.web.dto.admin.AdminReportedCommentDto;
import com.zpop.web.dto.admin.AdminReportedMeetingDto;
import com.zpop.web.dto.admin.AdminReportedMemberDto;

@Service
public class DefaultAdminReportService implements AdminReportService{

	private final ReportedMemberDao reportedMemberDao;
	private final ReportedMeetingDao reportedMeetingDao;
	private final ReportedCommentDao reportedCommentDao;
	
	@Autowired
	public DefaultAdminReportService (ReportedMemberDao reportedMemberDao,
								ReportedMeetingDao reportedMeetingDao,
								ReportedCommentDao reportedCommentDao) {
		this.reportedMemberDao = reportedMemberDao;
		this.reportedMeetingDao = reportedMeetingDao;
		this.reportedCommentDao = reportedCommentDao;
	}
	
	@Override
	public List<AdminReportedMemberDto> getReportedMembers(int page, String keyword, String option) {
		int size = 10;
		int offset=(page-1)*size;
		List<AdminReportedMemberDto> list = reportedMemberDao.getAdminViewList(size, offset, keyword, option);
		
		return list;
	}

	@Override
	public List<AdminReportedCommentDto> getReportedComments(int page, String keyword, String option) {
		int size = 10;
		int offset=(page-1)*size;
		List<AdminReportedCommentDto> list = reportedCommentDao.getAdminViewList(size, offset, keyword, option);
		
		return list;
	}

	@Override
	public List<AdminReportedMeetingDto> getReportedMeetings(int page, String keyword, String option) {
		int size = 10;
		int offset=(page-1)*size;
		List<AdminReportedMeetingDto> list = reportedMeetingDao.getAdminViewList(size, offset, keyword, option);
		
		return list;
	}

	@Override
	public int countReportedMembers(String keyword, String option) {
		int count = reportedMemberDao.count(keyword, option);
		return count;
	}

	@Override
	public int countReportedComments(String keyword, String option) {
		int count = reportedCommentDao.count(keyword, option);
		return count;
	}

	@Override
	public int countReportedMeetings(String keyword, String option) {
		int count = reportedMeetingDao.count(keyword, option);
		return count;
	}

}
