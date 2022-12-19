package com.zpop.web.dto;

import com.zpop.web.entity.meeting.MeetingThumbView;

import java.util.Date;

public class MeetingThumbnailResponse {
    private int id;
    private String category;
    private String region;
    private String ageRange;
    private String genderCategory;
    private int maxMember;
    private String title;
    private Date startedAt;
    private int viewCount;
    private int commentCount;
    private boolean isClosed;

    public MeetingThumbnailResponse() {
    }

    public MeetingThumbnailResponse(int id, String category, String region, String ageRange, String genderCategory, int maxMember, String title, Date startedAt, int viewCount, int commentCount, boolean isClosed) {
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
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getRegion() {
        return region;
    }

    public String getAgeRange() {
        return ageRange;
    }

    public String getGenderCategory() {
        return genderCategory;
    }

    public int getMaxMember() {
        return maxMember;
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

    public static MeetingThumbnailResponse of (MeetingThumbView entity) {
        String genderCategory = "누구나";
        switch (entity.getGenderCategory()) {
            case 1:
                genderCategory = "남자 모임";
                break;
            case 2:
                genderCategory = "여자 모임";
                break;
        }

        return new MeetingThumbnailResponse(
                entity.getId(),
                entity.getCategory(),
                entity.getRegion(),
                entity.getAgeRange(),
                genderCategory,
                entity.getMaxMember(),
                entity.getTitle(),
                entity.getStartedAt(),
                entity.getViewCount(),
                entity.getCommentCount(),
                entity.isClosed()
        );
    }
}
