package com.sparta.springhw2.dto;

import com.sparta.springhw2.entity.User;
        import lombok.Getter;

@Getter
public class UserTokenResponseDto {
    private final Long id;
    private final String username;
    private final String accessToken;
    private final String refreshToken;

    public UserTokenResponseDto(User user, String accessToken, String refreshToken){
        this.id = user.getId();
        this.username = user.getUsername();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
