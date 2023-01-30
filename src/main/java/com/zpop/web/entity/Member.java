package com.zpop.web.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Member {
    private int id;
    private int socialTypeId;
    private String socialId;
    private String nickname;
    private int fame;
    private String profileImagePath;
    private Date createdAt;
    private Date updatedAt;
    private Date resignedAt;

}
