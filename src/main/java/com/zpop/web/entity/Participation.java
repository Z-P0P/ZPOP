package com.zpop.web.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Participation {
    private int id;
    private int meetingId;
    private int participantId;
    private Date createdAt;
    private Date bannedAt;
    private Date canceledAt;
    private boolean hasEvaluated;


    public Participation(int meetingId, int participantId) {
        this.meetingId = meetingId;
        this.participantId = participantId;
    }


}
