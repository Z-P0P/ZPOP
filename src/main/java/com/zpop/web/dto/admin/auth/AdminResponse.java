package com.zpop.web.dto.admin.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminResponse {
    private int id;
    private String nickname;

    public AdminResponse(int id, String username) {
        this.id = id;
        this.nickname = username;
    }
}
