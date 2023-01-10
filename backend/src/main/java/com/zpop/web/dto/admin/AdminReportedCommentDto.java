package com.zpop.web.dto.admin;

import java.sql.Date;

public class AdminReportedCommentDto {
	
	private int id;
	private String reporterNickname;
	private int reporterId;
	private String original;
	private String writerNickname;
	private int writerId;
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
	public String getOriginal() {
		return original;
	}
	public void setOriginal(String original) {
		this.original = original;
	}
	public String getWriterNickname() {
		return writerNickname;
	}
	public void setWriterNickname(String writerNickname) {
		this.writerNickname = writerNickname;
	}
	public int getWriterId() {
		return writerId;
	}
	public void setWriterId(int writerId) {
		this.writerId = writerId;
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
