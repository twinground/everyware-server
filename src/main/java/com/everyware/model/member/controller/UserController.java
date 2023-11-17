package com.everyware.model.member.controller;

import com.everyware.model.member.dto.UserSignUpRequestDto;
import com.everyware.model.member.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @CrossOrigin
    @PostMapping("/signUp")
    public ResponseEntity signUp(@RequestBody UserSignUpRequestDto userSignUpRequestDto) throws Exception {
        try {
            userService.signUp(userSignUpRequestDto);
            return ResponseEntity.ok("회원가입 성공");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패: " + e.getMessage());
        }
    }
}
