package com.everyware.model.member.dto;

import lombok.Getter;

@Getter
public class UserLoginRequestDTO {
    private String email;
    private String password;
}
