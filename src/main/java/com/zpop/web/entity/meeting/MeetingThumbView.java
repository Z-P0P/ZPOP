package com.zpop.web.entity.meeting;

import java.util.Date;

public class MeetingThumbView {
    private int id;
    private int categoryId;
    private String category;
    private int regionId;
    private String ageRange;
    private int ageRangeId;
    private String region;
    private int maxMember;
    private int genderCategory;
    private String exposedGender;
    private String title;
    private Date startedAt;
    private int viewCount;
    private int commentCount;
    private boolean isClosed;

    public MeetingThumbView() {
    }

    public MeetingThumbView(int id, int categoryId, String category, int regionId, String ageRange, int ageRangeId, String region, int maxMember, int genderCategory, String exposedGender, String title, Date startedAt, int viewCount, int commentCount, boolean isClosed) {
        this.id = id;
        this.categoryId = categoryId;
        this.category = category;
        this.regionId = regionId;
        this.ageRange = ageRange;
        this.ageRangeId = ageRangeId;
        this.region = region;
        this.maxMember = maxMember;
        this.genderCategory = genderCategory;
        this.exposedGender = exposedGender;
        this.title = title;
        this.startedAt = startedAt;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.isClosed = isClosed;
    }

    public int getId() {
        return id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategory() {
        return category;
    }

    public int getRegionId() {
        return regionId;
    }

    public String getAgeRange() {
        return ageRange;
    }

    public int getAgeRangeId() {
        return ageRangeId;
    }

    public String getRegion() {
        return region;
    }

    public int getMaxMember() {
        return maxMember;
    }

    public int getGenderCategory() {
        return genderCategory;
    }

    public String getExposedGender() {
        return exposedGender;
    }

    public String getTitle() {
        return title;
    }

    public Date getStartedAt() {
        return startedAt;
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

}
