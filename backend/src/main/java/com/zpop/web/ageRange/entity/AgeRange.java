package com.zpop.web.ageRange.entity;

import com.zpop.web.ageRange.enums.AgeRangeType;
import com.zpop.web.global.BaseTimeEntity;
import com.zpop.web.meeting.entity.Meeting;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AgeRange extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private AgeRangeType type;

    @OneToMany(mappedBy = "ageRange")
    private List<Meeting> meetings;
}
