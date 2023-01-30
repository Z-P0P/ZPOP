package com.zpop.web.global.exception;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ExceptionResponseBody {
    private final int status;
    private final String message;
    private final List<String> details;
}
