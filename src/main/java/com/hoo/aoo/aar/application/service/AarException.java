package com.hoo.aoo.aar.application.service;

import lombok.Getter;

@Getter
public class AarException extends RuntimeException {

    private final AarErrorCode error;
    private final String message;

    public AarException(AarErrorCode error) {
        this.error = error;
        this.message = error.getMessage();
    }

    public AarException(AarErrorCode error, String message) {
        this.error = error;
        this.message = message;
    }
}
