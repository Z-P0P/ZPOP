package com.zpop.web.socialType.entity;

import com.zpop.web.global.BaseTimeEntity;
import com.zpop.web.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialType extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 20, nullable = false)
    private String name;

    @OneToMany(mappedBy = "socialType")
    private List<Member> member;
}
