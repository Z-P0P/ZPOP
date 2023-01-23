package com.zpop.web.dto;

public class RequestMemberReportDto {

	private int reportType;
	private String reportReason;

	RequestMemberReportDto(){

	}

	public RequestMemberReportDto(int reportType, String reportReason) {
		this.reportType = reportType;
		this.reportReason = reportReason;
	}

	public int getReportType() {
		return reportType;
	}

	public void setReportType(int reportType) {
		this.reportType = reportType;
	}

	public String getReportReason() {
		return reportReason;
	}

	public void setReportReason(String reportReason) {
		this.reportReason = reportReason;
	}
	
}
