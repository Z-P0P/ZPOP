package com.zpop.web.dto.admin;

import java.sql.Date;

public class AdminMeetingDto {
	
	private int id;
	private String title;
	private String hostNickname;
	private int participantsNum;
	private int maxMember;
	private Date createdAt;
	private boolean isClosed;
	private boolean isDeleted;

	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHostNickname() {
		return hostNickname;
	}
	public void setHostNickname(String hostNickname) {
		this.hostNickname = hostNickname;
	}
	public int getMaxMember() {
		return maxMember;
	}
	public void setMaxMember(int maxMember) {
		this.maxMember = maxMember;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public boolean isClosed() {
		return isClosed;
	}
	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}

	public int getParticipantsNum() {
		return participantsNum;
	}
	public void setParticipantsNum(int participantsNum) {
		this.participantsNum = participantsNum;
	}
	
	
}
