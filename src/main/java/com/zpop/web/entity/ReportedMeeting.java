package com.zpop.web.entity;

import java.util.Date;

public class ReportedMeeting {
	
	private int id;
	private int meetingId;
	private int reporterId;
	private int typeId;
	private int adminId;
	private String reason;
	private String originalTitle;
	private String original;
	private Date createdAt;
	private Date processedAt;
	private Date blockedAt;
	private Date releasedAt;
	
	public ReportedMeeting(int meetingId, int reporterId, int typeId, String reason,
			String originalTitle, String original) {
	
		this.meetingId = meetingId;
		this.reporterId = reporterId;
		this.typeId = typeId;
		this.reason = reason;
		this.originalTitle = originalTitle;
		this.original = original;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(int meetingId) {
		this.meetingId = meetingId;
	}

	public int getReporterId() {
		return reporterId;
	}

	public void setReporterId(int reporterId) {
		this.reporterId = reporterId;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}
	


	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getProcessedAt() {
		return processedAt;
	}

	public void setProcessedAt(Date processedAt) {
		this.processedAt = processedAt;
	}

	public Date getBlockedAt() {
		return blockedAt;
	}

	public void setBlockedAt(Date blockedAt) {
		this.blockedAt = blockedAt;
	}

	public Date getReleasedAt() {
		return releasedAt;
	}

	public void setReleasedAt(Date releasedAt) {
		this.releasedAt = releasedAt;
	}


	
}
