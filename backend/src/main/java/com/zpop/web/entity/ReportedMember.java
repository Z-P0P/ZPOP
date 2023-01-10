package com.zpop.web.entity;

import java.util.Date;

public class ReportedMember {
	
	private int id;
	private int reportedId;
	private int reporterId;
	private int typeId;
	private int adminId;
	private String reason;
	private Date createdAt;
	private Date processedAt;
	private Date blockedAt;
	private Date releasedAt;
	
	public ReportedMember(int reportedId, int reporterId, int typeId, String reason
			) {

		this.reportedId = reportedId;
		this.reporterId = reporterId;
		this.typeId = typeId;
		this.reason = reason;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getReportedId() {
		return reportedId;
	}

	public void setReportedId(int reportedId) {
		this.reportedId = reportedId;
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
