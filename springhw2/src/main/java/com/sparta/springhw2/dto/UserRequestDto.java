package com.sparta.springhw2.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String passwordCheck;


}
