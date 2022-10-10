package com.sparta.springhw2.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponse {
    private String ACCESS_TOKEN;
    private String REFRESH_TOKEN;
}
