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

    public Participation(int id, int meetingId, int participantId, Date createdAt, Date bannedAt, Date canceledAt) {
        this.id = id;
        this.meetingId = meetingId;
        this.participantId = participantId;
        this.createdAt = createdAt;
        this.bannedAt = bannedAt;
        this.canceledAt = canceledAt;
    }

    public Participation(int meetingId, int participantId) {
        this.meetingId = meetingId;
        this.participantId = participantId;
    }

    public int getId() {
        return id;
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
