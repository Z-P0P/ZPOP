package com.zpop.web.dto.admin;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AdminParticipationDto {

	private int id;
	private String title;
	private int meetingId;
	private String profileImagePath;
	private String participantNickname;
	private int participantId;
	@JsonFormat(pattern = "yyyy-MM-dd HH 시 mm 분")
	private Date createdAt;
	private Date canceledAt;
	
	
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
	public int getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(int meetingId) {
		this.meetingId = meetingId;
	}
	public String getProfileImagePath() {
		return profileImagePath;
	}
	public void setProfileImagePath(String profileImagePath) {
		this.profileImagePath = profileImagePath;
	}
	public String getParticipantNickname() {
		return participantNickname;
	}
	public void setParticipantNickname(String participantNickname) {
		this.participantNickname = participantNickname;
	}
	public int getParticipantId() {
		return participantId;
	}
	public void setParticipantId(int participantId) {
		this.participantId = participantId;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getCanceledAt() {
		return canceledAt;
	}
	public void setCanceledAt(Date canceledAt) {
		this.canceledAt = canceledAt;
	}
	
	
}
