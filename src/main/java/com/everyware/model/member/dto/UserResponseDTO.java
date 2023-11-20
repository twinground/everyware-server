package com.everyware.model.member.dto;


import com.everyware.model.jwt.TokenInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponseDTO {

    TokenInfo tokenInfo;
    String nickName;
}
