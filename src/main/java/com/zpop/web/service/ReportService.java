package com.zpop.web.service;

import com.zpop.web.entity.ReportedComment;
import com.zpop.web.entity.ReportedMeeting;
import com.zpop.web.entity.ReportedMember;

public interface ReportService {
	
	void createMeetingReport(ReportedMeeting reportedMeeting);
	void createMemberReport(ReportedMember reportedMember);
	void createCommentReport(ReportedComment reportedComment);
	
	int[] getCommentId(int commentId, int reporterId);
	
}
