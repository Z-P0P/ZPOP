package com.zpop.web.dto;

public class RequestCommentReportDto {

	private String reportType;
	private String reportReason;

	RequestCommentReportDto(){

	}

	public RequestCommentReportDto(String reportType, String reportReason) {
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
		return "RequestCommentReportDto [reportType=" + reportType + ", reportReason=" + reportReason + "]";
	}
	
}
