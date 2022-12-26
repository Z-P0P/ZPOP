package com.zpop.web.dto;

public class MyMeetingResponse {

    private String categoryName;
    private String regionName;
    private String startedAt;
    private String title;
    private String age;
    private int maxMember;
    private String genderCategory;
    private boolean isClosed;
    private int viewCount;
    private int commentCount;
    private int meetingId;
    private int participantId;
    private int regMemberId;

    public MyMeetingResponse() {
    }

    public MyMeetingResponse(String categoryName, String regionName, String startedAt, String title, String age, int maxMember, String genderCategory, boolean isClosed, int viewCount, int commentCount, int meetingId, int participantId, int regMemberId) {
        this.categoryName = categoryName;
        this.regionName = regionName;
        this.startedAt = startedAt;
        this.title = title;
        this.age = age;
        this.maxMember = maxMember;
        this.genderCategory = genderCategory;
        this.isClosed = isClosed;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.meetingId = meetingId;
        this.participantId = participantId;
        this.regMemberId = regMemberId;
    }

    public MyMeetingResponse(String categoryName, String regionName, String dateTime, String title, String age, int maxMember, String genderCategory, int isClosed, int viewCount, int commentCount, int meetingId, int participantId, int regMemberId) {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getMaxMember() {
        return maxMember;
    }

    public void setMaxMember(int maxMember) {
        this.maxMember = maxMember;
    }

    public String getGenderCategory() {
        return genderCategory;
    }

    public void setGenderCategory(String genderCategory) {
        this.genderCategory = genderCategory;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
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

    public int getRegMemberId() {
        return regMemberId;
    }

    public void setRegMemberId(int regMemberId) {
        this.regMemberId = regMemberId;
    }

    @Override
    public String toString() {
        return "MyMeetingResponse{" +
                "categoryName='" + categoryName + '\'' +
                ", regionName='" + regionName + '\'' +
                ", startedAt='" + startedAt + '\'' +
                ", title='" + title + '\'' +
                ", age='" + age + '\'' +
                ", maxMember=" + maxMember +
                ", genderCategory='" + genderCategory + '\'' +
                ", isClosed=" + isClosed +
                ", viewCount=" + viewCount +
                ", commentCount=" + commentCount +
                ", meetingId=" + meetingId +
                ", participantId=" + participantId +
                ", regMemberId=" + regMemberId +
                '}';
    }
}
