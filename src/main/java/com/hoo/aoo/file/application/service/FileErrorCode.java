package com.hoo.aoo.file.application.service;

import com.hoo.aoo.file.domain.FileSize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FileErrorCode {

    NEW_FILE_CREATION_FAILED("Error has been occurred when write new file.", HttpStatus.INTERNAL_SERVER_ERROR),
    FILE_SIZE_LIMIT_EXCEED("File Size Exceed " + FileSize.FILE_SIZE_LIMIT, HttpStatus.BAD_REQUEST),
    FILE_NAME_DUPLICATION("File name is duplicated.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;

}
