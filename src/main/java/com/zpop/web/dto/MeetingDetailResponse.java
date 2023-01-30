package com.zpop.web.dto;

import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
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

}
