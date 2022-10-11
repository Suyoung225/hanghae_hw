package com.sparta.springhw2.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(value = { RequestException.class })
    public ResponseEntity<Object> handleApiRequestException(RequestException e) {

        return new ResponseEntity<>(
                e.getMessage(),
                e.getHttpStatus()
        );
    }

}
