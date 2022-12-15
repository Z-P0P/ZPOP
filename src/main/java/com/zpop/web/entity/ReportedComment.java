package com.zpop.web.entity;

import java.util.Date;

public class ReportedComment {
	
	private int id;
	private int commentId;
	private int reporterId;
	private int typeId;
	private int adminId;
	private String reason;
	private String original;
	private Date createdAt;
	private Date processedAt;
	private Date blockedAt;
	private Date releasedAt;
	
	public ReportedComment(int id, int commentId, int reporterId, int typeId, int adminId, String reason,
			String original, Date createdAt, Date processedAt, Date blockedAt, Date releasedAt) {
		this.id = id;
		this.commentId = commentId;
		this.reporterId = reporterId;
		this.typeId = typeId;
		this.adminId = adminId;
		this.reason = reason;
		this.original = original;
		this.createdAt = createdAt;
		this.processedAt = processedAt;
		this.blockedAt = blockedAt;
		this.releasedAt = releasedAt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
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
