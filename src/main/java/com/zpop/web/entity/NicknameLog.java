package com.zpop.web.entity;

import java.util.Date;

import lombok.Builder;
import lombok.AllArgsConstructor;
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
public class NicknameLog {
    private int id;
    private int memberId;
    private Date createdAt;
    private String nickname;

    public NicknameLog(int memberId, String nickname){
        this.memberId = memberId;
        this.nickname = nickname;
    }
}
