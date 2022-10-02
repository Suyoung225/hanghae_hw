package com.sparta.week04_shop.domain;

import lombok.Getter;

@Getter
public class ProductRequestDto {
    private String title;
    private String link;
    private String image;
    private int lprice;
}
