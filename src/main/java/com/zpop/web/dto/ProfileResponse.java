package com.zpop.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProfileResponse {
    
    private int id;
    private String nickname;
    private int fame;
    private String profileImg;
    private boolean isResigned;
    private int participatedCount;



    public ProfileResponse(int id, String nickname, int fame, String profileImg, boolean isResigned, int participatedCount) {
        this.id = id;
        this.nickname = nickname;
        this.fame = fame;
        this.profileImg = profileImg;
        this.isResigned = isResigned;
        this.participatedCount = participatedCount;
    }
}
