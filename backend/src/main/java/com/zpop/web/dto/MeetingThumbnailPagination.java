package com.zpop.web.dto;

public class MeetingThumbnailPagination {
    private Integer startId; // 요청 시작 id
    private String keyword; // 검색어
    private Integer categoryId; 
    private String[] regionIds;
    private Boolean isClosed;

    public MeetingThumbnailPagination() {
    }

    public MeetingThumbnailPagination(int startId, String keyword, Integer categoryId, String[] regionIds, Boolean isClosed) {
        this.startId = startId;
        this.keyword = keyword;
        this.categoryId = categoryId;
        this.regionIds = regionIds;
        this.isClosed = isClosed;
    }

    public int getStartId() {
        return startId;
    }

    public String getKeyword(){
        return keyword;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public String[] getRegionIds() {
        return regionIds;
    }
    

    public Boolean isClosed() {
        return isClosed;
    }
}
