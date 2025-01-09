package com.hoo.aoo.aar.application.service;

import com.hoo.aoo.common.application.service.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum AarErrorCode implements ErrorCode {

    INVALID_PHONE_NUMBER_ERROR("AAR-AUTHN-1",BAD_REQUEST,"잘못된 전화번호 형식입니다."),
    SNS_ACCOUNT_NOT_FOUND( "AAR-AUTHN-2",NOT_FOUND,"SNS 계정을 찾을 수 없습니다."),
    NICK_NAME_CONFLICT("AAR-AUTHN-3",CONFLICT,"중복된 닉네임입니다."),
    ALREADY_REGISTERED_SNS_ACCOUNT("AAR-AUTHN-4",CONFLICT,"이미 등록된 SNS 계정입니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;

}
