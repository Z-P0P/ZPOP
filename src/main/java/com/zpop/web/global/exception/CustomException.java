package com.zpop.web.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ExceptionReason reason;

    public CustomException(ExceptionReason reason) {
        super(reason.getMessage());
        this.reason = reason;
    }
}