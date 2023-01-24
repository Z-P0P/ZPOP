package com.zpop.web.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ExceptionReason {
    // 400
    VALIDATION_ERROR(BAD_REQUEST, "잘못된 입력이 있습니다."),

    // 401
    AUTHENTICATION_ERROR(UNAUTHORIZED, "인증 에러"),

    // 403
    AUTHORIZATION_ERROR(FORBIDDEN, "권한 에러");

    private final HttpStatus httpStatus;
    private final String message;

    ExceptionReason(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
