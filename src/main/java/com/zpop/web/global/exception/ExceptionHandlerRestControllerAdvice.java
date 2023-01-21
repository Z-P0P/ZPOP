package com.zpop.web.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ExceptionHandlerRestControllerAdvice {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity hanldeResponseStatusException(ResponseStatusException e) {
        return new ResponseEntity(e.getMessage(), e.getStatusCode());
    }
}
