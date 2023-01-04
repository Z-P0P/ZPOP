package com.zpop.web.entity;

import java.sql.Date;

public class MeetingFile {
	
	private int id;
	private int meetingId;
	private String name;
	private Date createdAt;
	private Date deletedAt;
	
	public MeetingFile(String name) {
		this(name,0);
	}
	
	public MeetingFile(String name, int meetingId) {
		this.name = name;
		this.meetingId = meetingId;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getDeletedAt() {
		return deletedAt;
	}
	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}
	
	@Override
	public String toString() {
		return "MeetingImage [id=" + id + ", meetingId=" + meetingId + ", name=" + name + ", createdAt=" + createdAt
				+ ", deletedAt=" + deletedAt + "]";
	}
	
	
}
