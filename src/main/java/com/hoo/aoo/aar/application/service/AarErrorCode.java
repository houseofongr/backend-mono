package com.hoo.aoo.aar.application.service;

import com.hoo.aoo.common.application.service.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum AarErrorCode implements ErrorCode {

    NICK_NAME_CONFLICT("AAR-AUTHN-1",CONFLICT,"사용자 닉네임이 중복됩니다."),
    SNS_ACCOUNT_NOT_FOUND( "AAR-AUTHN-2",NOT_FOUND,"SNS 계정을 찾을 수 없습니다."),
    ALREADY_REGISTERED_SNS_ACCOUNT("AAR-AUTHN-3",CONFLICT,"이미 등록된 SNS 계정입니다."),
    INVALID_PHONE_NUMBER_ERROR("AAR-AUTHN-4",BAD_REQUEST,"잘못된 전화번호입니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;

}
