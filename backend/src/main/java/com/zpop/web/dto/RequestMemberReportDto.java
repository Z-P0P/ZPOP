package com.zpop.web.dto;

public class RequestMemberReportDto {

	private String reportType;
	private String reportReason;

	RequestMemberReportDto(){

	}

	public RequestMemberReportDto(String reportType, String reportReason) {
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
		return "RequestMemberReportDto [reportType=" + reportType + ", reportReason=" + reportReason + "]";
	}
	
}
