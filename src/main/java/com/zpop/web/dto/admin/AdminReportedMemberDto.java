package com.zpop.web.dto.admin;

import java.sql.Date;

public class AdminReportedMemberDto {
	
	private int id;
	private String reporterNickname;
	private int reporterId;
	private String reportedNickname;
	private int reportedId;
	private String reportType;
	private Date processedAt;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getReporterNickname() {
		return reporterNickname;
	}
	public void setReporterNickname(String reporterNickname) {
		this.reporterNickname = reporterNickname;
	}
	public int getReporterId() {
		return reporterId;
	}
	public void setReporterId(int reporterId) {
		this.reporterId = reporterId;
	}
	public String getReportedNickname() {
		return reportedNickname;
	}
	public void setReportedNickname(String reportedNickname) {
		this.reportedNickname = reportedNickname;
	}
	public int getReportedId() {
		return reportedId;
	}
	public void setReportedId(int reportedId) {
		this.reportedId = reportedId;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public Date getProcessedAt() {
		return processedAt;
	}
	public void setProcessedAt(Date processedAt) {
		this.processedAt = processedAt;
	}
	
}
