package com.zpop.web.dto;

import java.util.Date;
import java.util.List;

public class MeetingDetailResponse {
    private int id;
    private String title;
    private Date startedAt;
    private String textStartedAt;
    private String detailRegion;
    private String content;
    private String categoryName;
    private String regionName;
    private int maxMember;
    private String genderCategory;
    private String ageRange;
    private int regMemberId;
    private int viewCount;
    private int commentCount;
    private boolean isMyMeeting;
    private boolean hasParticipated;
    private boolean isClosed;
    private List<ParticipantResponse> participants;
    private List<CommentResponse> comments;


    public MeetingDetailResponse() {
    }

    public MeetingDetailResponse(int id, String title, Date startedAt, String textStartedAt, String detailRegion, String content, String categoryName, String regionName, int maxMember, String genderCategory, String ageRange, int regMemberId, int viewCount, int commentCount, boolean isMyMeeting, boolean hasParticipated, boolean isClosed, List<ParticipantResponse> participants, List<CommentResponse> comments) {
        this.id = id;
        this.title = title;
        this.startedAt = startedAt;
        this.textStartedAt = textStartedAt;
        this.detailRegion = detailRegion;
        this.content = content;
        this.categoryName = categoryName;
        this.regionName = regionName;
        this.maxMember = maxMember;
        this.genderCategory = genderCategory;
        this.ageRange = ageRange;
        this.regMemberId = regMemberId;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.isMyMeeting = isMyMeeting;
        this.hasParticipated = hasParticipated;
        this.isClosed = isClosed;
        this.participants = participants;
        this.comments = comments;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartedAt() {
        return this.startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public String getTextStartedAt() {
        return this.textStartedAt;
    }

    public void setTextStartedAt(String textStartedAt) {
        this.textStartedAt = textStartedAt;
    }

    public String getDetailRegion() {
        return this.detailRegion;
    }

    public void setDetailRegion(String detailRegion) {
        this.detailRegion = detailRegion;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getRegionName() {
        return this.regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public int getMaxMember() {
        return this.maxMember;
    }

    public void setMaxMember(int maxMember) {
        this.maxMember = maxMember;
    }

    public String getGenderCategory() {
        return this.genderCategory;
    }

    public void setGenderCategory(String genderCategory) {
        this.genderCategory = genderCategory;
    }

    public String getAgeRange() {
        return this.ageRange;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
    }

    public int getRegMemberId() {
        return this.regMemberId;
    }

    public void setRegMemberId(int regMemberId) {
        this.regMemberId = regMemberId;
    }

    public int getViewCount() {
        return this.viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getCommentCount() {
        return this.commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public boolean isMyMeeting() {
        return this.isMyMeeting;
    }

   
    public void setMyMeeting(boolean isMyMeeting) {
        this.isMyMeeting = isMyMeeting;
    }

    public boolean hasParticipated() {
        return this.hasParticipated;
    }

    public void setParticipated(boolean hasParticipated) {
        this.hasParticipated = hasParticipated;
    }

    public boolean isClosed() {
        return this.isClosed;
    }

    public void setClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }

    public List<ParticipantResponse> getParticipants() {
        return this.participants;
    }

    public void setParticipants(List<ParticipantResponse> participants) {
        this.participants = participants;
    }

    public List<CommentResponse> getComments() {
        return this.comments;
    }

    public void setComments(List<CommentResponse> comments) {
        this.comments = comments;
    }

}
