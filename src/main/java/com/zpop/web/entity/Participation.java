package com.zpop.web.entity;

import java.util.Date;

public class Participation {
    private int id;
    private int meetingId;
    private int participantId;
    private Date createdAt;
    private Date bannedAt;
    private Date canceledAt;

    public Participation() {
    }

    public Participation(int meetingId, int participantId) {
        this.meetingId = meetingId;
        this.participantId = participantId;
    }

    public int getId() {
        return id;
    }

    @Override
	public String toString() {
		return "Participation [id=" + id + ", meetingId=" + meetingId + ", participantId=" + participantId
				+ ", createdAt=" + createdAt + ", bannedAt=" + bannedAt + ", canceledAt=" + canceledAt + "]";
	}

	public int getMeetingId() {
        return meetingId;
    }

    public int getParticipantId() {
        return participantId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getBannedAt() {
        return bannedAt;
    }

    public Date getCanceledAt() {
        return canceledAt;
    }
}
