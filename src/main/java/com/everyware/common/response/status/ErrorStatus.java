package com.everyware.common.response.status;

import com.everyware.common.response.BaseErrorCode;
import com.everyware.common.response.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),


    // 멤버 관련 에러
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4001", "사용자가 없습니다."),
    MEMBER_LOGIN_FAILURE(HttpStatus.BAD_REQUEST, "MEMBER4003", "아이디 혹은 비밀번호를 잘못 입력하였습니다."),
    NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4002", "닉네임은 필수 입니다."),


    MEMBER_SIGNUP_ERROR(HttpStatus.BAD_REQUEST, "SIGNUP4001", "회원가입 유효성 검사 실패"),
    EMAIL_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "SIGNUP4002", "이미 존재하는 이메일입니다."),
    BOOTH_NOT_EXIST(HttpStatus.NOT_FOUND, "BOOTH4001", "부스가 존재하지 않습니다."),
    BOOTH_MEETING_ERROR(HttpStatus.BAD_REQUEST,"BOOTH4001","부스 미팅의 예상치 못한 오류가 발생했습니다. 다시 시도해주세요"),
    BOOTH_MEETING_ALREADY_EXISTS_IN_BOOTH(HttpStatus.BAD_REQUEST,"BOOTH4002", "해당 부스에 이미 예약하셨습니다."),
    BOOTH_MEETING_ALREADY_EXISTS_IN_TIME(HttpStatus.BAD_REQUEST, "BOOTH4003","해당 시간에 다른 예약이 있습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}
