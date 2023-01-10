package com.zpop.web.member.entity;

import com.zpop.web.global.BaseTimeEntity;
import com.zpop.web.meeting.entity.Meeting;
import com.zpop.web.notification.entity.Notification;
import com.zpop.web.participation.entity.Participation;
import com.zpop.web.socialType.entity.SocialType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private SocialType socialType;

    private String socialId;

    @Column(length = 20)
    private String nickname;

    @Column(columnDefinition = "INT DEFAULT 0")
    private int fame;

    private String profileImagePath;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isSuspended;

    private LocalDateTime resignedAt;

    @OneToMany(mappedBy = "member")
    private List<Meeting> meetings;

    @OneToMany(mappedBy = "member")
    private List<Participation> participations;

    @OneToMany(mappedBy = "member")
    private List<Notification> notifications;
}