package com.zpop.web.entity.meeting;

import java.util.Date;

public class Meeting {
    private int id;
    private int regMemberId;
    private int categoryId;
    private int regionId;
    private int ageRangeId;
    private int genderCategory;
    private String title;
    private String content;
    private String detailRegion;
    private int maxMember;
    private Date startedAt;
    private String contact;
    private int viewCount;
    private int commentCount;
    private boolean isClosed;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

    public Meeting() {
    }

    public Meeting(int regMemberId, int categoryId, int regionId, int ageRangeId, int genderCategory, String title, String content, String detailRegion, int maxMember, Date startedAt, String contact) {
        this.regMemberId = regMemberId;
        this.categoryId = categoryId;
        this.regionId = regionId;
        this.ageRangeId = ageRangeId;
        this.genderCategory = genderCategory;
        this.title = title;
        this.content = content;
        this.detailRegion = detailRegion;
        this.maxMember = maxMember;
        this.startedAt = startedAt;
        this.contact = contact;
    }

    public int getId() {
        return id;
    }

    public int getRegMemberId() {
        return regMemberId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getRegionId() {
        return regionId;
    }

    public int getAgeRangeId() {
        return ageRangeId;
    }

    public int getGenderCategory() {
        return genderCategory;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDetailRegion() {
        return detailRegion;
    }

    public int getMaxMember() {
        return maxMember;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public String getContact() {
        return contact;
    }

    public int getViewCount() {
        return viewCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }
}
