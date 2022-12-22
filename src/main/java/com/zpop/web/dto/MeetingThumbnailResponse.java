package com.zpop.web.dto;

import com.zpop.web.entity.meeting.MeetingThumbnailView;

import java.util.Date;

public class MeetingThumbnailResponse {
    private int id;
    private String category;
    private String region;
    private String ageRange;
    private String genderCategory;
    private int maxMember;
    private String title;
    private String startedAt;
    private int viewCount;
    private int commentCount;
    private boolean isClosed;

    public MeetingThumbnailResponse() {
    }


    public MeetingThumbnailResponse(int id, String category, String region, String ageRange, String genderCategory, int maxMember, String title, String startedAt, int viewCount, int commentCount, boolean isClosed) {
        this.id = id;
        this.category = category;
        this.region = region;
        this.ageRange = ageRange;
        this.genderCategory = genderCategory;
        this.maxMember = maxMember;
        this.title = title;
        this.startedAt = startedAt;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.isClosed = isClosed;
    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAgeRange() {
        return this.ageRange;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
    }

    public String getGenderCategory() {
        return this.genderCategory;
    }

    public void setGenderCategory(String genderCategory) {
        this.genderCategory = genderCategory;
    }

    public int getMaxMember() {
        return this.maxMember;
    }

    public void setMaxMember(int maxMember) {
        this.maxMember = maxMember;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartedAt() {
        return this.startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
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

    public boolean isIsClosed() {
        return this.isClosed;
    }

    public boolean getIsClosed() {
        return this.isClosed;
    }

    public void setIsClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }

}
