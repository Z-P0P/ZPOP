package com.zpop.web.dto;

public class ProfileResponse {
    
    private int id;
    private String nickname;
    private int fame;
    private String profileImg;
    private boolean isResigned;
    private int participatedMeetingNumber;

    public ProfileResponse() {
    }

    public ProfileResponse(int id, String nickname, int fame, String profileImg, boolean isResigned, int participatedMeetingNumber) {
        this.id = id;
        this.nickname = nickname;
        this.fame = fame;
        this.profileImg = profileImg;
        this.isResigned = isResigned;
        this.participatedMeetingNumber = participatedMeetingNumber;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public int getFame() {
        return fame;
    }
    public void setFame(int fame) {
        this.fame = fame;
    }
    public String getProfileImg() {
        return profileImg;
    }
    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
    public boolean isResigned() {
        return isResigned;
    }
    public void setResigned(boolean isResigned) {
        this.isResigned = isResigned;
    }
    public int getParticipatedMeetingNumber() {
        return participatedMeetingNumber;
    }

    public void setParticipatedMeetingNumber(int participatedMeetingNumber) {
        this.participatedMeetingNumber = participatedMeetingNumber;
    }

}
