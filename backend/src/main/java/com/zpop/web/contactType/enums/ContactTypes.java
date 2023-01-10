package com.zpop.web.contactType.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ContactTypes {
    KAKAO("카카오"),
    ZOOM("줌"),
    NAVER_BAND("밴드"),
    DISCORD("디스코드"),
    OLDEST("50대 이상");

    private final String value;
}
