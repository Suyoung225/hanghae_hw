package com.sparta.springhw.dto;

import lombok.Getter;

@Getter
public class PostingRequestDto{
    private String title;
    private String author;
    private Long password;
    private String content;
}
