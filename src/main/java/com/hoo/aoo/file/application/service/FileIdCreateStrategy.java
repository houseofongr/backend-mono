package com.hoo.aoo.file.application.service;

import com.hoo.aoo.file.domain.FileId;
import com.hoo.aoo.file.domain.exception.FileExtensionMismatchException;

public interface FileIdCreateStrategy {
    FileId create(String originalFilename, String fileSystemName) throws FileExtensionMismatchException;
}
