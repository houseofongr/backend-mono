package com.aoo.file.application.service;

import com.aoo.common.domain.Authority;
import com.aoo.file.domain.FileId;
import com.aoo.file.domain.FileType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BasicFileIdCreateStrategy implements FileIdCreateStrategy {

    private final String baseDir;
    private final Authority authority;
    private final FileType fileType;

    @Override
    public FileId create(String originalFilename, String fileSystemName) {
        return FileId.create(baseDir, authority, fileType, originalFilename, fileSystemName);
    }
}
