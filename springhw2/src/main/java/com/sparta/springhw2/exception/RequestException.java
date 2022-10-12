package com.sparta.springhw2.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RequestException extends RuntimeException{
    private final HttpStatus httpStatus;

    public RequestException(ErrorCode errorcode) {
        super(errorcode.getMessage() ); // RuntimeException 클래스의 생성자를 호출합니다.
        this.httpStatus = errorcode.getHttpStatus();
    }
}
