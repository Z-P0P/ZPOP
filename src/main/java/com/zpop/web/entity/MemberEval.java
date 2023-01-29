package com.zpop.web.entity;

import java.sql.Timestamp;

public class MemberEval {
    private int id;
    private int meetingId;
    private int evaluatorId;
    private int evaluateeId;
    private int result;
    private Timestamp createdAt;

    public MemberEval (int meetingId, int evaluatorId, int evaluateeId, int result){
        this.meetingId = meetingId;
        this.evaluatorId =evaluatorId;
        this.evaluateeId = evaluateeId;
        this.result = result;
    }
    
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMeetingId() {
        return this.meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public int getEvaluatorId() {
        return this.evaluatorId;
    }

    public void setEvaluatorId(int evaluatorId) {
        this.evaluatorId = evaluatorId;
    }

    public int getEvaluateeId() {
        return this.evaluateeId;
    }

    public void setEvaluateeId(int evaluateeId) {
        this.evaluateeId = evaluateeId;
    }

    public int getResult() {
        return this.result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Timestamp getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "MemberEval{" +
                "id=" + id +
                ", meetingId=" + meetingId +
                ", evaluatorId=" + evaluatorId +
                ", evaluateeId=" + evaluateeId +
                ", result=" + result +
                ", createdAt=" + createdAt +
                '}';
    }

}
