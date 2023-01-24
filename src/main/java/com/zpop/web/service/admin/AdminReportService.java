package com.zpop.web.service.admin;

import java.util.Date;
import java.util.List;

import com.zpop.web.dto.admin.AdminReportedCommentDto;
import com.zpop.web.dto.admin.AdminReportedMeetingDto;
import com.zpop.web.dto.admin.AdminReportedMemberDto;

public interface AdminReportService {

	List<AdminReportedMemberDto> getReportedMembers(int page, String keyword, String option, Date minDate, Integer period, Integer num, String order);

	List<AdminReportedCommentDto> getReportedComments(int page, String keyword, String option, Date minDate, Integer period, Integer num, String order);
	
	List<AdminReportedMeetingDto> getReportedMeetings(int page, String keyword, String option, Date minDate, Integer period, Integer num, String order);
	
	int countReportedMembers(String keyword, String option, Date minDate, Integer period);

	int countReportedComments(String keyword, String option, Date minDate, Integer period);

	int countReportedMeetings(String keyword, String option, Date minDate, Integer period);

    int cancelMemberReport(List<Integer> ids);

    int cancelMeetingReport(List<Integer> ids);

    int cancelCommentReport(List<Integer> ids);

	int suspendMemberReported(List<Integer> ids, Integer period);

    int suspendMeetingReported(List<Integer> ids, Integer period);

    int suspendCommentReported(List<Integer> ids, Integer period);

}
