package com.everyware.model.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserSignUpRequestDto {

    private String email;
    private String password;
    private String nickname;
    private int age;
    private String city;
}
