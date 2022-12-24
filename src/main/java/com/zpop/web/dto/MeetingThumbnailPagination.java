package com.zpop.web.dto;

public class MeetingThumbnailPagination {
    private Integer startId; // 요청 시작 id
    private String keyword; // 검색어
    private Integer categoryId; 
    private Boolean isClosed;

    public MeetingThumbnailPagination() {
    }

    public MeetingThumbnailPagination(int startId, String keyword, Integer categoryId, Boolean isClosed) {
        this.startId = startId;
        this.keyword = keyword;
        this.categoryId = categoryId;
        this.isClosed = isClosed;
    }

    public int getStartId() {
        return startId;
    }

    public String getKeyword(){
        return keyword;
    }

    public Integer getCategoryId() {
        return this.categoryId;
    }
    

    public Boolean isClosed() {
        return isClosed;
    }
}
