package com.everyware.model.member.service;

import com.everyware.BaseException;
import com.everyware.ErrorCode;
import com.everyware.model.member.Member;
import com.everyware.model.member.dto.UserSignUpRequestDto;
import com.everyware.model.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(UserSignUpRequestDto userSignUpRequestDto) throws Exception {

        if (memberRepository.findByEmail(userSignUpRequestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        if (memberRepository.findByNickname(userSignUpRequestDto.getNickname()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }

        Member member = Member.builder()
                .email(userSignUpRequestDto.getEmail())
                .password(userSignUpRequestDto.getPassword())
                .nickname(userSignUpRequestDto.getNickname())
                .build();
        member.passwordEncode(passwordEncoder);
        memberRepository.save(member);
    }

    public Member findByEmail(String email) {
        Member member =
                memberRepository
                        .findByEmail(email)
                        .orElseThrow(() -> BaseException.from(ErrorCode.USER_NOT_FOUND));
        return member;
    }
}
