package com.zpop.web.dto;

public class RequestMeetingReportDto {

	private String reportType;
	private String reportReason;

	RequestMeetingReportDto(){

	}

	public RequestMeetingReportDto(String reportType, String reportReason) {
		this.reportType = reportType;
		this.reportReason = reportReason;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getReportReason() {
		return reportReason;
	}

	public void setReportReason(String reportReason) {
		this.reportReason = reportReason;
	}

	@Override
	public String toString() {
		return "RequestMeetingReportDto [reportType=" + reportType + ", reportReason=" + reportReason + "]";
	}
	
}
