package com.zpop.web.meeting.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {

    MALE("남성 모임"),
    FEMALE("여성 모임"),
    ALL("남녀 모두");

    private final String value;
}
