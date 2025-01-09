package com.hoo.aoo.admin.application.service;

import com.hoo.aoo.common.application.service.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum AdminErrorCode implements ErrorCode {

    PIXEL_LIMIT_EXCEED("ADMIN-HOUSE-1", BAD_REQUEST, "이미지 픽셀 크기 한도를 초과했습니다."),
    IMAGE_SIZE_EXCEED("ADMIN-HOUSE-2", BAD_REQUEST, "이미지 크기 한도를 초과했습니다."),
    ROOM_NAME_DUPLICATED("ADMIN-HOUSE-3", CONFLICT, "방 이름이 중복됩니다."),
    IMAGE_ID_NOT_FOUND("ADMIN-HOUSE-4", BAD_REQUEST, "메타데이터에 등록되지 않은 이미지가 존재합니다."),
    IMAGE_FILE_NOT_FOUND("ADMIN-HOUSE-5", BAD_REQUEST, "메타데이터에 등록된 이미지가 존재하지 않습니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;

}
