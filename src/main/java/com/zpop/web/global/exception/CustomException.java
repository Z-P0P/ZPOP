package com.zpop.web.global.exception;

import java.util.List;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ExceptionReason reason;
    private final List<String> details;

    public CustomException(ExceptionReason reason) {
        super(reason.getMessage());
        this.reason = reason;
        this.details = null;
    }

    public CustomException(ExceptionReason reason, List<String> details) {
        super(reason.getMessage());
        this.reason = reason;
        this.details = details;
    }
}