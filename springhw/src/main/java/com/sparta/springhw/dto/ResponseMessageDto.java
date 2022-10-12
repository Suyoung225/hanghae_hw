package com.sparta.springhw.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseMessageDto <T>{
    boolean success;
    T data;
    String error;

    @Builder
    public ResponseMessageDto(boolean success, T data, String error) {
        this.success = success;
        this.data = data;
        this.error = error;
    }
}
