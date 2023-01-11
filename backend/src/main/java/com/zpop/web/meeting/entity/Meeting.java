package com.zpop.web.meeting.entity;

import com.zpop.web.ageRange.entity.AgeRange;
import com.zpop.web.category.entity.Category;
import com.zpop.web.contactType.entity.ContactType;
import com.zpop.web.global.BaseTimeEntity;
import com.zpop.web.meeting.enums.Gender;
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

    @Column(nullable = false)
    private Gender genderCategory;

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

    /**
     * 모임의 마감 여부를 받는다
     * @return 마감 여부
     */
    public boolean isClosed(){
        // 마감 시간이 있다면 true 리턴
        if(closedAt != null)
            return true;

        // 모임 시작 시간이 지났다면, 마감처리 후 true 리턴
        if (startedAt.isBefore(LocalDateTime.now())) {
            closedAt = LocalDateTime.now();
            return true;
        }

        return false;
    }

    /**
     * 내가 주최한 모임인지 확인한다
     * @param member
     * @return 내가 주최한지 여부
     */
    public boolean isMyMeeting(Member member){
        if(member == null)
            return false;

        Member host = this.member;

        return host.getId() == member.getId();
    }

    /**
     * 모임에 참여한 회원인지 확인한다
     * @param member
     * @return 회원 참여 여부
     */
    public boolean hasParticipated(Member member) {
        if(member == null)
            return false;

        for(Participation p : participations) {
            // 취소 or bann 당한 참여자는 스킵
            if(p.getCanceledAt() != null || p.getBannedAt() != null)
                continue;

            // 참여한 모임인지 확인
            if(p.getMember().getId() == member.getId())
                return true;
        }

        return false;
    }
}
