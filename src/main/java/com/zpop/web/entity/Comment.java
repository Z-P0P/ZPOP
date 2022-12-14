package com.zpop.web.entity;

import java.util.Date;

public class Comment {
	
	private int id;
	private int meetingId;
	private int writerId;
	private int parentCommentId;
	private String content;
	private Date createdAt;
	private Date updatedAt;
	private Date deletedAt;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setMeetingId(int meetingId) {
		this.meetingId = meetingId;
	}
	public int getWriterId() {
		return writerId;
	}
	public void setWriterId(int writerId) {
		this.writerId = writerId;
	}
	public int getParentCommentId() {
		return parentCommentId;
	}
	public void setParentCommentId(int parentCommentId) {
		this.parentCommentId = parentCommentId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Date getDeletedAt() {
		return deletedAt;
	}
	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", meetingId=" + meetingId + ", writerId=" + writerId + ", parentCommentId="
				+ parentCommentId + ", content=" + content + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", deletedAt=" + deletedAt + "]";
	}
	
}
