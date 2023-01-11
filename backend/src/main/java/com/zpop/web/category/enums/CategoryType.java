package com.zpop.web.category.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryType {
    FOOD_AND_CAFE("밥/카페"),
    WORKOUT("운동"),
    STUDY("스터디"),
    ESCAPE_ROOM("방탈출"),
    BOARD_GAME("보드게임"),
    CULTURE("문화"),
    PET("펫"),
    ETC("기타");

    private final String value;
}
