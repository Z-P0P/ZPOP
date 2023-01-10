package com.zpop.web.notification.entity;

import com.zpop.web.global.BaseTimeEntity;
import com.zpop.web.member.entity.Member;
import com.zpop.web.notification.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String url;

    @Column(nullable = false)
    private NotificationType type;

    private LocalDateTime readAt;
}
