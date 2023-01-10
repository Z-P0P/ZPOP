package com.zpop.web.ageRange.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AgeRangeType {
    TEENAGER("10대"),
    TWENITES("20대"),
    THIRITES("30대"),
    FORTIES("40대"),
    OLDEST("50대 이상");

    private final String value;
}


