package com.sparta.springhw2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public ResponseEntity<Object> handleApiRequestException(IllegalArgumentException ex) {
        RestApiException restApiException = new RestApiException();
        restApiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        restApiException.setErrorMessage(ex.getMessage());

        return new ResponseEntity<>(
                restApiException,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = { NotAuthorizedException.class })
    public ResponseEntity<Object> handleApiRequestException(NotAuthorizedException ex) {
        RestApiException restApiException = new RestApiException();
        restApiException.setHttpStatus(HttpStatus.FORBIDDEN);
        restApiException.setErrorMessage(ex.getMessage());

        return new ResponseEntity<>(
                restApiException,
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(value = { NoSuchElementException.class })
    public ResponseEntity<Object> handleApiRequestException(NoSuchElementException ex) {
        RestApiException restApiException = new RestApiException();
        restApiException.setHttpStatus(HttpStatus.NOT_FOUND);
        restApiException.setErrorMessage(ex.getMessage());

        return new ResponseEntity<>(
                restApiException,
                HttpStatus.NOT_FOUND
        );
    }



}
