package com.zpop.web.dto;

public class EvalMemberDto {


    private int id;
    private String title;
    private int meetingId;
    private int participantId;
    private String nickname;
    private int regMemberId;

    public EvalMemberDto() {
    }

    public EvalMemberDto(int id, String title, int meetingId, int participantId, String nickname, int regMemberId) {
        this.id = id;
        this.title = title;
        this.meetingId = meetingId;
        this.participantId = participantId;
        this.nickname = nickname;
        this.regMemberId = regMemberId;
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

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public int getParticipantId() {
        return participantId;
    }

    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getRegMemberId() {
        return regMemberId;
    }

    public void setRegMemberId(int regMemberId) {
        this.regMemberId = regMemberId;
    }

    @Override
    public String toString() {
        return "EvalMemberDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", meetingId=" + meetingId +
                ", participantId=" + participantId +
                ", nickname='" + nickname + '\'' +
                ", regMemberId=" + regMemberId +
                '}';
    }
}
