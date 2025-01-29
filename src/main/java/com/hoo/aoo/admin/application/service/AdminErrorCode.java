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
    INVALID_CREATE_HOUSE_METADATA("ADMIN-HOUSE-3", BAD_REQUEST, "하우스 생성 메타데이터를 읽을 수 없습니다."),
    IMAGE_FILE_NOT_FOUND("ADMIN-HOUSE-4", BAD_REQUEST, "하우스 생성 메타데이터에 등록된 이미지가 존재하지 않습니다."),
    HOLDING_HOME_HOUSE_DELETE("ADMIN-HOUSE-5", BAD_REQUEST, "홈을 보유한 하우스는 삭제할 수 없습니다."),
    HOUSE_NOT_FOUND("ADMIN-HOUSE-6", NOT_FOUND, "해당 하우스를 찾을 수 없습니다."),
    ROOM_NOT_FOUND("ADMIN-HOUSE-7", NOT_FOUND, "존재하지 않는 방입니다."),
    ILLEGAL_HOUSE_RELATIONSHIP("ADMIN-HOUSE-8", INTERNAL_SERVER_ERROR, "하우스와 방 사이에 잘못된 참조 관계가 존재합니다."),

    USER_NOT_FOUND("ADMIN-USER-1", NOT_FOUND, "해당 사용자를 찾을 수 없습니다."),

    HOME_NOT_FOUND("ADMIN-HOME-1", NOT_FOUND, "해당 홈을 찾을 수 없습니다."),
    ALREADY_CREATED_HOME("ADMIN-HOME-2", CONFLICT, "이미 동일한 요청으로 생성된 홈이 존재합니다."),

    ILLEGAL_SHAPE_TYPE("ADMIN-ITEM-1", BAD_REQUEST, "잘못된 아이템 형태입니다."),
    ITEM_HAS_SOUND_SOURCE("ADMIN-ITEM-2", BAD_REQUEST, "음원이 포함된 아이템은 해당 요청을 수행할 수 없습니다."),
    ITEM_NOT_FOUND("ADMIN-ITEM-3", NOT_FOUND, "해당 아이템을 찾을 수 없습니다."),

    SOUND_SOURCE_NOT_FOUND("ADMIN-SOUNDSOURCE-1", NOT_FOUND, "해당 음원을 찾을 수 없습니다."),

    LOAD_ENTITY_FAILED("ADMIN-COMMON-1", INTERNAL_SERVER_ERROR, "객체를 불러오는데 실패했습니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;

}
