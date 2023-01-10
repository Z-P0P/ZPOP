package com.zpop.web.region.entity;

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
public class Region extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private CategoryType type;

    @OneToMany(mappedBy = "region")
    private List<Meeting> meetings;
}
