package com.zpop.web.dto.admin;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AdminMemberEvalDto {
	private int id;
	private int evaluateeId;
	private String evaluateeNickname;
	private int evaluatorId;
	private String evaluatorNickname;
	private int rate;
	private int meetingId;
	private String meetingTitle;
	@JsonFormat(pattern="yyyy-MM-dd HH시 mm분")
	private Date createdAt;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEvaluateeId() {
		return evaluateeId;
	}
	public void setEvaluateeId(int evaluateeId) {
		this.evaluateeId = evaluateeId;
	}
	public String getEvaluateeNickname() {
		return evaluateeNickname;
	}
	public void setEvaluateeNickname(String evaluateeNickname) {
		this.evaluateeNickname = evaluateeNickname;
	}
	public int getEvaluatorId() {
		return evaluatorId;
	}
	public void setEvaluatorId(int evaluatorId) {
		this.evaluatorId = evaluatorId;
	}
	public String getEvaluatorNickname() {
		return evaluatorNickname;
	}
	public void setEvaluatorNickname(String evaluatorNickname) {
		this.evaluatorNickname = evaluatorNickname;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public int getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(int meetingId) {
		this.meetingId = meetingId;
	}
	public String getMeetingTitle() {
		return meetingTitle;
	}
	public void setMeetingTitle(String meetingTitle) {
		this.meetingTitle = meetingTitle;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
}
