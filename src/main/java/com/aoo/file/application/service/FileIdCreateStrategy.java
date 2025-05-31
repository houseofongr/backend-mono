package com.aoo.file.application.service;

import com.aoo.file.domain.FileId;

public interface FileIdCreateStrategy {
    FileId create(String originalFilename, String fileSystemName);
}
