package com.hoo.aoo.file.application.service;

import com.hoo.aoo.common.application.service.ApplicationException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileException extends ApplicationException {

    public FileException(Exception e,  FileErrorCode error) {
        super(error);
        log.error("File error has been constructed with Exception : {}", e.getMessage());
    }

    public FileException(FileErrorCode error) {
        super(error);
        log.error("File error has been constructed.");
    }

}
