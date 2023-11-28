package com.everyware.common.response.status;

import com.everyware.common.response.BaseCode;
import com.everyware.common.response.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

    // 일반적인 응답
    _OK(HttpStatus.OK, "COMMON200", "성공입니다."),

    // 회원가입 응답
    _SIGNUP_SUCCESS(HttpStatus.OK, "SIGNUP200", "회원가입 성공입니다."),
    _LOGIN_SUCCESS(HttpStatus.OK, "LOGIN200", "로그인 성공입니다."),

    IMAG_UPLOAD_SUCCESS(HttpStatus.OK, "IMAGE200", "이미지 업로드 성공입니다."),
    // 멤버 관련 응답
    GET_PROFILE_SUCCESS(HttpStatus.OK,"USER200","프로필 가져오기 성공"),
    // ~~~ 관련 응답
    //댓글

    CREAT_NEIGHBORHOOD_POST_COMMENT_SUCCESS(HttpStatus.CREATED, "NEIGHBORHOOD_COMMENT_201", "댓글 생성 성공"),
    GET_NEIGHBORHOOD_COMMENTS_SUCCESS(HttpStatus.OK, "NEIGHBORHOOD_COMMENT_200", "댓글들 조회 성공"),


    CREATE_NEIGHBORHOOD_POST_HEART_SUCCESS(HttpStatus.CREATED, "NEIGHBORHOOD_HEARD_201", "좋아요 생성 성공"),
    CANCEL_NEIGHBORHOOD_POST_HEART_SUCCESS(HttpStatus.OK, "NEIGHBORHOOD_HEARD_200", "좋아요 취소 성공"),
    CREAT_BOOTH_MEET_SUCCESS(HttpStatus.CREATED,"BOOTH_MEET_201","부스 예약 성공")
    ;



    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}
