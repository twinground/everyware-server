package com.everyware.common.exception;
import com.everyware.common.response.BaseErrorCode;

public class BoothHandler extends GeneralException{
    public BoothHandler(BaseErrorCode code) {
        super(code);
    }
}
