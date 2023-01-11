package com.zpop.web.meeting.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MeetingResponse {
    private int id;
    private String title;
    private LocalDateTime startedAt;
    private String detailRegion;
    private String content;
    private String category;
    private String region;
    private int maxMember;
    private String genderCategory;
    private String ageRange;
    private int regMemberId;
    private int viewCount;
    private int commentCount;
    private boolean isMyMeeting;
    private boolean hasParticipated;
    private boolean isClosed;
}
