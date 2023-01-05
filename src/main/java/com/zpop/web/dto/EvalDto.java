package com.zpop.web.dto;

import com.zpop.web.entity.MemberEval;

import java.util.List;

public class EvalDto {
    private int meetingId;
    private int evaluatorId;
    private List<MemberEval> evals;

    public EvalDto() {
    }

    public EvalDto(int meetingId, int evaluatorId, List<MemberEval> evals) {
        this.meetingId = meetingId;
        this.evaluatorId = evaluatorId;
        this.evals = evals;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public int getEvaluatorId() {
        return evaluatorId;
    }

    public void setEvaluatorId(int evaluatorId) {
        this.evaluatorId = evaluatorId;
    }

    public List<MemberEval> getEvals() {
        return evals;
    }

    public void setEvals(List<MemberEval> evals) {
        this.evals = evals;
    }

    @Override
    public String toString() {
        return "EvalDto{" +
                "meetingId=" + meetingId +
                ", evaluatorId=" + evaluatorId +
                ", evals=" + evals +
                '}';
    }
}
