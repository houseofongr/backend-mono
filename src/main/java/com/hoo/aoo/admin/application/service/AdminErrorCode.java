package com.hoo.aoo.admin.application.service;

import com.hoo.aoo.common.application.service.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@AllArgsConstructor
public enum AdminErrorCode implements ErrorCode {

    FILE_SIZE_LIMIT_EXCEED("ADMIN-HOUSE-1", BAD_REQUEST, "파일 용량 한도를 초과했습니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;

}
