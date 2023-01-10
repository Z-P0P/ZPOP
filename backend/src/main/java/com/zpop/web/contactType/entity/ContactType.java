package com.zpop.web.contactType.entity;

import com.zpop.web.category.enums.CategoryType;
import com.zpop.web.contactType.enums.ContactTypes;
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
public class ContactType extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private ContactTypes type;

    @OneToMany(mappedBy = "contactType")
    private List<Meeting> meetings;
}
