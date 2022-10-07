package com.sparta.test1.dto;

import lombok.Getter;

import java.util.List;

public class CommonResponse <T>{

    private T result;

    public CommonResponse(T result) {
        this.result = result;
    }

    @Getter
    public static class ofList<T> {
        private List<T> result;

        public ofList(List<T> data){
            this.result = data;
        }
    }
}