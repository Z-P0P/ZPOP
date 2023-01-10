package com.zpop.web.participation.entity;

import com.zpop.web.global.BaseTimeEntity;
import com.zpop.web.meeting.entity.Meeting;
import com.zpop.web.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Participation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Meeting meeting;

    private LocalDateTime bannedAt;

    private LocalDateTime canceledAt;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT 0")
    private boolean hasEvaluated;
}
