package com.hoo.aoo.aar.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum AarErrorCode {

    NICK_NAME_CONFLICT(CONFLICT, "User nickname is conflict."),
    SNS_ACCOUNT_NOT_FOUND(NOT_FOUND, "SNS Account not found.");

    private final HttpStatus status;
    private final String message;

}
