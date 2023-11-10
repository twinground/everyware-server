package com.everyware.model.member.controller;

import com.everyware.model.member.dto.UserSignUpRequestDto;
import com.everyware.model.member.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signUp")
    public String signUp(@RequestBody UserSignUpRequestDto userSignUpRequestDto) throws Exception {
        userService.signUp(userSignUpRequestDto);
        return "회원가입 성공";
    }

    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }
}
