package com.zpop.web.entity.member;

import java.util.Date;

public class MyMeetingView {
    private String categoryName;
    private String regionName;
    private Date startedAt;
    private String title;
    private String age;
    private int maxMember;
    private int genderCategory;
    private boolean isClosed;
    private int viewCount;
    private int commentCount;
    private int meetingId;
    private int participantId;
    private int regMemberId;

    public MyMeetingView() {
    }

    public MyMeetingView(String categoryName, String regionName, Date startedAt, String title, String age, int maxMember, int genderCategory, boolean isClosed, int viewCount, int commentCount, int meetingId, int participantId, int regMemberId) {
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

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
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

    public int getGenderCategory() {
        return genderCategory;
    }

    public void setGenderCategory(int genderCategory) {
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
        return "MyMeetingView{" +
                "categoryName='" + categoryName + '\'' +
                ", regionName='" + regionName + '\'' +
                ", startedAt=" + startedAt +
                ", title='" + title + '\'' +
                ", age='" + age + '\'' +
                ", maxMember=" + maxMember +
                ", genderCategory=" + genderCategory +
                ", isClosed=" + isClosed +
                ", viewCount=" + viewCount +
                ", commentCount=" + commentCount +
                ", meetingId=" + meetingId +
                ", participantId=" + participantId +
                ", regMemberId=" + regMemberId +
                '}';
    }
}
