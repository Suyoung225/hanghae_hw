package com.sparta.springhw2.exception;

public class NotAuthorizedException extends RuntimeException{
    NotAuthorizedException(){

    }
    public NotAuthorizedException(String message) {
        super(message); // RuntimeException 클래스의 생성자를 호출합니다.
    }
}
