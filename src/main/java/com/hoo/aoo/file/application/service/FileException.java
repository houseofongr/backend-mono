package com.hoo.aoo.file.application.service;

import com.hoo.aoo.common.application.service.ApplicationException;
import lombok.Getter;

public class FileException extends ApplicationException {

    public FileException(FileErrorCode error) {
        super(error);
    }

    public FileException(FileErrorCode error, String message) {
        super(error, message);
    }
}
