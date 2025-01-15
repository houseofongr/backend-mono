package com.hoo.aoo.admin.application.service;

import com.hoo.aoo.common.application.service.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum AdminErrorCode implements ErrorCode {

    AXIS_PIXEL_LIMIT_EXCEED("ADMIN-HOUSE-1", BAD_REQUEST, "좌표 픽셀 크기 한도를 초과했습니다."),
    AREA_SIZE_LIMIT_EXCEED("ADMIN-HOUSE-2", BAD_REQUEST, "이미지 크기 한도를 초과했습니다."),
    INVALID_METADATA("ADMIN-HOUSE-3", BAD_REQUEST, "메타데이터를 읽을 수 없습니다."),
    IMAGE_FILE_NOT_FOUND("ADMIN-HOUSE-4", BAD_REQUEST, "메타데이터에 등록된 이미지가 존재하지 않습니다."),
    HOUSE_NOT_FOUND("ADMIN-HOUSE-5", NOT_FOUND, "해당 하우스를 찾을 수 없습니다."),
    ROOM_NOT_FOUND("ADMIN-HOUSE-6", NOT_FOUND, "해당 하우스에 존재하지 않는 방 이름이 존재합니다."),
    ROOM_NAME_DUPLICATED("ADMIN-HOUSE-7", CONFLICT, "중복되는 방 이름이 존재합니다."),
    ILLEGAL_HOUSE_RELATIONSHIP("ADMIN-HOUSE-8", INTERNAL_SERVER_ERROR, "하우스와 방 사이에 잘못된 참조 관계가 존재합니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;

}
