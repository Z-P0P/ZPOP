package com.zpop.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MyMeetingResponse {

    private String category;
    private String region;
    private String startedAt;
    private String title;
    private String age;
    private int maxMember;
    private String genderCategory;
    private boolean isClosed;
    private int viewCount;
    private int commentCount;


    private int meetingId;
    private int participantId;
    private int regMemberId;
    private boolean canRate;
    private boolean isEvaluated;

}