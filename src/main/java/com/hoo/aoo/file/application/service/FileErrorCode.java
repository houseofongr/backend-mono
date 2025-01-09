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

    NEW_FILE_CREATION_FAILED("FILE-PUBLIC-1", INTERNAL_SERVER_ERROR, "새로운 파일을 생성하는 과정에서 오류가 발생했습니다."),
    FILE_SIZE_LIMIT_EXCEED("FILE-PUBLIC-2", BAD_REQUEST, "파일 용량 한도를 초과했습니다."),
    FILE_NAME_DUPLICATION("FILE-PUBLIC-3", BAD_REQUEST, "파일 이름이 중복됩니다."),
    RETRIEVE_FILE_FAILED("FILE-PUBLIC-4", INTERNAL_SERVER_ERROR, "파일을 불러오는 데 실패했습니다."),
    FILE_NOT_FOUND("FILE-PUBLIC-5", NOT_FOUND, "파일을 찾을 수 없습니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;

}
