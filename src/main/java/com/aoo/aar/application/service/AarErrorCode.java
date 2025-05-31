package com.aoo.aar.application.service;

import com.aoo.common.application.service.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum AarErrorCode implements ErrorCode {

    INVALID_PHONE_NUMBER_ERROR("AAR-AUTHN-1", BAD_REQUEST, "잘못된 전화번호 형식입니다."),

    NOT_OWNED_HOME("AAR-AUTHN-2", FORBIDDEN, "본인이 소유하지 않은 홈에 접근했습니다."),
    NOT_OWNED_ROOM("AAR-AUTHN-3", FORBIDDEN, "본인이 소유하지 않은 룸에 접근했습니다."),
    NOT_OWNED_ITEM("AAR-AUTHN-4", FORBIDDEN, "본인이 소유하지 않은 아이템에 접근했습니다."),
    NOT_OWNED_SOUND_SOURCE("AAR-AUTHN-5", FORBIDDEN, "본인이 소유하지 않은 음원에 접근했습니다."),

    SNS_ACCOUNT_NOT_FOUND("AAR-AUTHN-6", NOT_FOUND, "SNS 계정을 찾을 수 없습니다."),

    NICK_NAME_CONFLICT("AAR-AUTHN-7", CONFLICT, "중복된 닉네임입니다."),
    ALREADY_REGISTERED_SNS_ACCOUNT("AAR-AUTHN-8", CONFLICT, "이미 등록된 SNS 계정입니다."),

    LOAD_ENTITY_FAILED("AAR-COMMON-1", INTERNAL_SERVER_ERROR, "객체를 불러오는데 실패했습니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;

}
