package com.hoo.aoo.admin.application.service;

import com.hoo.aoo.common.application.service.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AdminErrorCode implements ErrorCode {



    private final String code;
    private final HttpStatus status;
    private final String message;

}
