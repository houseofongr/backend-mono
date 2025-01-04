package com.hoo.aoo.aar.application.exception;

import com.hoo.aoo.common.enums.ErrorCode;
import lombok.Getter;

@Getter
public class AarException extends ApplicationException {

    private final ErrorCode error;
    private final String message;

    public AarException(ErrorCode error) {
        this.error = error;
        this.message = error.getMessage();
    }
}
