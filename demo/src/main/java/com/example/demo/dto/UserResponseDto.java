package com.example.demo.dto;

import com.example.demo.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private final Long id;
    private final String username;

    public UserResponseDto(User user){
        this.id = user.getId();
        this.username = user.getUsername();
    }

}
