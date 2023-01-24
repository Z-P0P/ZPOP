package com.zpop.web.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionHandlerRestControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseBody> handleAllException(Exception e) {

        ExceptionResponseBody errorResponse = ExceptionResponseBody.builder()
                .status(INTERNAL_SERVER_ERROR.value())
                .message("서버 에러")
                .build();

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponseBody> handleCustomException(CustomException e) {

        ExceptionReason reason = e.getReason();

        ExceptionResponseBody errorResponse = ExceptionResponseBody.builder()
                .status(reason.getHttpStatus().value())
                .message(reason.getMessage())
                .build();

        return ResponseEntity.status(reason.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseBody> handleValidationException(MethodArgumentNotValidException e) {
        // get all validation errors
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        List<String> details = new ArrayList<>();

        for(FieldError error : fieldErrors)
            details.add(error.getDefaultMessage());

        ExceptionResponseBody errorResponse = ExceptionResponseBody.builder()
                .status(BAD_REQUEST.value())
                .message(ExceptionReason.VALIDATION_ERROR.getMessage())
                .details(details)
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
