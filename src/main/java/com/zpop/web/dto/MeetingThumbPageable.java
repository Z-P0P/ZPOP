package com.zpop.web.dto;

public class MeetingThumbPageable {
    private Integer startId; // 요청 시작 id
    private String keyword; // 검색어
    private Boolean isClosed;

    public MeetingThumbPageable() {
    }

    public MeetingThumbPageable(int startId, String keyword, Boolean isClosed) {
        this.startId = startId;
        this.keyword = keyword;
        this.isClosed = isClosed;
    }

    public int getStartId() {
        return startId;
    }

    public String getKeyword(){
        return keyword;
    }

    public Boolean isClosed() {
        return isClosed;
    }
}
