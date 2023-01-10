package com.zpop.web.meeting.entity;

import com.zpop.web.ageRange.entity.AgeRange;
import com.zpop.web.category.entity.Category;
import com.zpop.web.contactType.entity.ContactType;
import com.zpop.web.global.BaseTimeEntity;
import com.zpop.web.member.entity.Member;
import com.zpop.web.participation.entity.Participation;
import com.zpop.web.region.entity.Region;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meeting extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY)
    private AgeRange ageRange;

    @ManyToOne(fetch = FetchType.LAZY)
    private ContactType contactType;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(length = 50, nullable = false)
    private String detailRegion;

    @Column(nullable = false)
    private int maxMember;

    @Column(nullable = false)
    private LocalDateTime startedAt;

    @Column(nullable = false)
    private String contact;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int viewCount;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int commentCount;

    private LocalDateTime closedAt;

    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "meeting")
    private List<Participation> participations;
}
