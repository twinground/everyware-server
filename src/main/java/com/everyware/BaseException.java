package com.everyware;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final ErrorCode errorCode;

    @Builder
    private BaseException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public static BaseException from(ErrorCode errorCode) {
        return BaseException.builder().errorCode(errorCode).build();
    }
}
