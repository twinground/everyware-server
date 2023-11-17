package com.everyware.model.member.service;

import com.everyware.BaseException;
import com.everyware.ErrorCode;
import com.everyware.model.jwt.JwtTokenProvider;
import com.everyware.model.jwt.TokenInfo;
import com.everyware.model.member.Member;
import com.everyware.model.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public TokenInfo login(String email, String password) {

        Member member =
                memberRepository
                        .findByEmail(email)
                        .orElseThrow(() -> BaseException.from(ErrorCode.USER_NOT_FOUND));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                member.getUsername(), password);
        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        return tokenInfo;

    }
}
