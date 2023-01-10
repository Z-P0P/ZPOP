package com.zpop.web.region.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RegionType {
    SEOUL("서울"),
    IN("인천/경기"),
    NAVER_BAND("경남/부산/울산"),
    DISCORD("충청/대전/세종"),
    OLDEST("전라/광주"),
    dd("강원"),
    asdf("경북/대구"),
    JEJU("제주"),
    ONLINE("온라인");

    private final String value;
}
