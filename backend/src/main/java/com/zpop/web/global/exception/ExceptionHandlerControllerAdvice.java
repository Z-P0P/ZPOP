package com.zpop.web.global.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(NoHandlerFoundException.class)
    public String notFoundPage(NoHandlerFoundException e) {
        return "error/404";
    }
}
