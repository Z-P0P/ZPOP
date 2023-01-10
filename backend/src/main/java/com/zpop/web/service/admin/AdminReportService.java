package com.zpop.web.service.admin;

import java.util.List;

import com.zpop.web.dto.admin.AdminReportedCommentDto;
import com.zpop.web.dto.admin.AdminReportedMeetingDto;
import com.zpop.web.dto.admin.AdminReportedMemberDto;

public interface AdminReportService {

	List<AdminReportedMemberDto> getReportedMembers(int page, String keyword, String option);

	List<AdminReportedCommentDto> getReportedComments(int page, String keyword, String option);
	
	List<AdminReportedMeetingDto> getReportedMeetings(int page, String keyword, String option);
	
	int countReportedMembers(String keyword, String option);

	int countReportedComments(String keyword, String option);

	int countReportedMeetings(String keyword, String option);

}
