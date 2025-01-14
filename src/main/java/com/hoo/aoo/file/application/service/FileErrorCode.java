package com.hoo.aoo.file.application.service;

import com.hoo.aoo.common.application.service.ErrorCode;
import com.hoo.aoo.file.domain.FileSize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum FileErrorCode implements ErrorCode {

    FILE_SIZE_LIMIT_EXCEED("FILE-PUBLIC-1", BAD_REQUEST, "파일 용량 한도를 초과했습니다."),
    INVALID_FILE_EXTENSION("FILE-PUBLIC-2", BAD_REQUEST, "유효하지 않은 파일 확장자입니다."),
    FILE_NAME_EMPTY("FILE-PUBLIC-3", BAD_REQUEST, "파일 이름이 존재하지 않습니다."),
    FILE_NOT_FOUND("FILE-PUBLIC-4", NOT_FOUND, "파일을 찾을 수 없습니다."),
    FILE_NAME_DUPLICATION("FILE-PUBLIC-5", CONFLICT, "중복된 파일명입니다."),
    RETRIEVE_FILE_FAILED("FILE-PUBLIC-6", INTERNAL_SERVER_ERROR, "파일을 불러오는 데 실패했습니다."),
    NEW_FILE_CREATION_FAILED("FILE-PUBLIC-7", INTERNAL_SERVER_ERROR, "새로운 파일을 생성하는 데 실패했습니다."),
    MAKE_FILES_PARENT_DIRECTORY_FAIL("FILE-PUBLIC-8", INTERNAL_SERVER_ERROR, "상위 디렉토리를 생성하는 데 실패했습니다."),
    ILLEGAL_FILE_TYPE_DIR("FILE-PUBLIC-9", INTERNAL_SERVER_ERROR, "잘못된 파일 타입 경로입니다."),
    ILLEGAL_FILE_AUTHORITY_DIR("FILE-PUBLIC-10", INTERNAL_SERVER_ERROR, "잘못된 파일 권한 경로입니다."),

    INVALID_AUTHORITY("FILE-PRIVATE-1", FORBIDDEN, "잘못된 권한의 파일 요청입니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;

}
