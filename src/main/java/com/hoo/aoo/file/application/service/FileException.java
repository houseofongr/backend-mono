package com.hoo.aoo.file.application.service;

import com.hoo.aoo.aar.application.service.AarErrorCode;
import lombok.Getter;

@Getter
public class FileException extends RuntimeException{
    private final FileErrorCode error;
    private final String message;

    public FileException (FileErrorCode error) {
        this.error = error;
        this.message = error.getMessage();
    }
}
