package com.zpop.web.service;

public interface ReportService {
	
	void createMeetingReport(int typeId, String reason);
	void createMemberReport(int typeId, String reason);
	void createCommentReport(int typeId, String reason);
	
}
