package com.zpop.web.dto;

public class RegisterMeetingResponse {

	private int meetingId;

	public RegisterMeetingResponse(int meetingId) {
		this.meetingId = meetingId;
	}
	
	
	public int getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(int meetingId) {
		this.meetingId = meetingId;
	}

}
