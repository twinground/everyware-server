package com.everyware.model.member.service;

import com.everyware.BaseException;
import com.everyware.ErrorCode;
import com.everyware.common.jwt.JwtTokenProvider;
import com.everyware.common.jwt.SecurityUtil;
import com.everyware.common.jwt.TokenInfo;
import com.everyware.model.member.Authority;
import com.everyware.model.member.Member;
import com.everyware.model.member.dto.Response;
import com.everyware.model.member.dto.UserLoginRequestDTO;
import com.everyware.model.member.dto.UserResponseDTO;
import com.everyware.model.member.dto.UserSignUpRequestDto;
import com.everyware.model.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final MemberRepository memberRepository;
    private final Response response;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public ResponseEntity<?> signUp(UserSignUpRequestDto signUp) {
        if (memberRepository.existsByEmail(signUp.getEmail())) {
            return response.fail("이미 회원가입된 이메일입니다.", HttpStatus.BAD_REQUEST);
        }

        Member user = Member.builder()
                .email(signUp.getEmail())
                .password(passwordEncoder.encode(signUp.getPassword()))
                .nickname(signUp.getNickname())
                .roles(Collections.singletonList(Authority.ROLE_USER.name()))
                .build();
        memberRepository.save(user);

        return response.success("회원가입에 성공했습니다.");
    }

    public ResponseEntity<?> login(UserLoginRequestDTO userLoginRequestDTO) {
        if (memberRepository.findByEmail(userLoginRequestDTO.getEmail()).orElse(null) == null) {
            return response.fail("아이디(이메일) 또는 비밀번호를 잘못 입력했습니다."
                    + "입력하신 내용을 다시 확인해주세요.", HttpStatus.BAD_REQUEST);
        }

        try {
            // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
            // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
            UsernamePasswordAuthenticationToken authenticationToken = userLoginRequestDTO.toAuthentication();

            // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
            // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
            Authentication authentication = authenticationManagerBuilder.getObject()
                    .authenticate(authenticationToken);

            // 3. 인증 정보를 기반으로 JWT 토큰 생성
            TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
            Member member = memberRepository.findByEmail(userLoginRequestDTO.getEmail())
                    .orElseThrow();
            return response.success(
                    UserResponseDTO.builder().tokenInfo(tokenInfo).nickName(member.getNickname())
                            .build()
                    , "로그인에 성공했습니다.", HttpStatus.OK);
        } catch (AuthenticationException e) {
            // Handle authentication failure, e.g., incorrect password
            return response.fail("아이디(이메일) 또는 비밀번호를 잘못 입력했습니다."
                    + "입력하신 내용을 다시 확인해주세요.", HttpStatus.UNAUTHORIZED);
        }
    }

    /*
    public ResponseEntity<?> reissue(UserRequestDto.Reissue reissue) {
        // 1. Refresh Token 검증
        if (!jwtTokenProvider.validateToken(reissue.getRefreshToken())) {
            return response.fail("Refresh Token 정보가 유효하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        // 2. Access Token 에서 User email 을 가져옵니다.
        Authentication authentication = jwtTokenProvider.getAuthentication(reissue.getAccessToken());

        // 3. Redis 에서 User email 을 기반으로 저장된 Refresh Token 값을 가져옵니다.
        String refreshToken = (String)redisTemplate.opsForValue().get("RT:" + authentication.getName());
        // (추가) 로그아웃되어 Redis 에 RefreshToken 이 존재하지 않는 경우 처리
        if(ObjectUtils.isEmpty(refreshToken)) {
            return response.fail("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }
        if(!refreshToken.equals(reissue.getRefreshToken())) {
            return response.fail("Refresh Token 정보가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        // 4. 새로운 토큰 생성
        UserResponseDto.TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        // 5. RefreshToken Redis 업데이트
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return response.success(tokenInfo, "Token 정보가 갱신되었습니다.", HttpStatus.OK);
    }

     */


    public ResponseEntity<?> authority() {
        // SecurityContext에 담겨 있는 authentication userEamil 정보
        String userEmail = SecurityUtil.getCurrentUserEmail();

        Member member = memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        // add ROLE_ADMIN
        member.getRoles().add(Authority.ROLE_ADMIN.name());
        memberRepository.save(member);

        return response.success();
    }

    public Member findByEmail(String email) {
        Member member =
                memberRepository
                        .findByEmail(email)
                        .orElseThrow(() -> BaseException.from(ErrorCode.USER_NOT_FOUND));
        return member;
    }

}
