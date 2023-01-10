package com.zpop.web.category.entity;

import com.zpop.web.ageRange.enums.AgeRangeType;
import com.zpop.web.category.enums.CategoryType;
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
public class Category extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private CategoryType type;

    @OneToMany(mappedBy = "category")
    private List<Meeting> meetings;
}
