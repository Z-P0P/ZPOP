package com.zpop.web.service;

import com.zpop.web.entity.ReportedComment;
import com.zpop.web.entity.ReportedMeeting;
import com.zpop.web.entity.ReportedMember;

public interface ReportService {
	
	void createMeetingReport(ReportedMeeting reportedMeeting);
	void createMemberReport(ReportedMember reportedMember);
	boolean createCommentReport(ReportedComment reportedComment);
	
	int[] getReportedCommentId(int commentId, int reporterId);
	int[] getReportedMeetingId(int meetingId, int reporterId);
}
