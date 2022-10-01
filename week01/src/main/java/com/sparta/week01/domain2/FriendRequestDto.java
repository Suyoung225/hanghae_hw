package com.sparta.week01.domain2;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FriendRequestDto {
    private String name;
    private int age;
    private String address;

    public FriendRequestDto(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }
}
