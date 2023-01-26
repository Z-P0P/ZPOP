package com.zpop.web.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ExceptionReason {
    // 400
    VALIDATION_ERROR(BAD_REQUEST, "잘못된 입력이 있습니다."),
    CLOSED_MEETING(BAD_REQUEST, "마감된 모임입니다."),
    PARTICIPANTS_EXISTS(BAD_REQUEST, "참가자가 있어 모임을 삭제할 수 없습니다"),

    // 401
    AUTHENTICATION_ERROR(UNAUTHORIZED, "인증 에러"),

    // 403
    AUTHORIZATION_ERROR(FORBIDDEN, "권한 에러"),
    KICKED_MEMBER(FORBIDDEN, "모임에 참여할 수 없습니다"),

    // 404
    NOT_FOUND_MEETING(NOT_FOUND, "존재하지 않는 모임입니다."),

    // 409
    ALREADY_PARTICIPATED(CONFLICT, "이미 참여한 모임입니다.");


    private final HttpStatus httpStatus;
    private final String message;

    ExceptionReason(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
