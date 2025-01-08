package com.hoo.aoo.aar.application.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum AarErrorCode {

    NICK_NAME_CONFLICT("User nickname is conflict.", CONFLICT),
    SNS_ACCOUNT_NOT_FOUND( "SNS Account not found.", NOT_FOUND),
    ALREADY_REGISTERED_SNS_ACCOUNT("Already Registered Sns Account.", BAD_REQUEST),
    INVALID_PHONE_NUMBER_ERROR("Invalid phone number.", BAD_REQUEST);

    private final String message;
    private final HttpStatus status;

}
