package com.everyware.model.member.service;

import com.everyware.BaseException;
import com.everyware.ErrorCode;
import com.everyware.model.member.Role;
import com.everyware.model.member.User;
import com.everyware.model.member.dto.UserSignUpRequestDto;
import com.everyware.model.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(UserSignUpRequestDto userSignUpRequestDto) throws Exception {

        if (userRepository.findByEmail(userSignUpRequestDto.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        if (userRepository.findByNickname(userSignUpRequestDto.getNickname()).isPresent()) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }

        User user = User.builder()
                .email(userSignUpRequestDto.getEmail())
                .password(userSignUpRequestDto.getPassword())
                .nickname(userSignUpRequestDto.getNickname())
                .role(Role.USER)
                .build();

        user.passwordEncode(passwordEncoder);
        userRepository.save(user);
    }
    public User findByEmail(String email){
        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> BaseException.from(ErrorCode.USER_NOT_FOUND));
        return user;
    }
}
