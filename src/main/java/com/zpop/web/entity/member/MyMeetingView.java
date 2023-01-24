package com.zpop.web.entity.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Getter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class MyMeetingView {
    private String categoryName;
    private String regionName;
    private Date startedAt;
    private String title;
    private String age;
    private int maxMember;
    private int genderCategory;
    private Date closedAt;
    private int viewCount;
    private int commentCount;
    private int meetingId;
    private int participantId;
    private int regMemberId;
    private int hasEvaluated;
}
