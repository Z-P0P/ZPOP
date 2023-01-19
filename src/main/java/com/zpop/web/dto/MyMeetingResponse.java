package com.zpop.web.dto;

public class MyMeetingResponse {

    private String category;
    private String region;
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
    private boolean canRate;

    public MyMeetingResponse() {
    }
    // TODO - 롬복 적용하기
    public MyMeetingResponse(String category, String region, String startedAt, String title, String age, int maxMember, String genderCategory, boolean isClosed, int viewCount, int commentCount, int meetingId, int participantId, int regMemberId, boolean canRate) {
        this.category = category;
        this.region = region;
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
        this.canRate = canRate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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

    public boolean isCanRate() {
        return canRate;
    }

    public void setCanRate(boolean canRate) {
        this.canRate = canRate;
    }
}