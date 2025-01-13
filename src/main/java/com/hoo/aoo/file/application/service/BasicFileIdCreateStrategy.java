package com.hoo.aoo.file.application.service;

import com.hoo.aoo.common.domain.Authority;
import com.hoo.aoo.file.domain.FileId;
import com.hoo.aoo.file.domain.FileType;
import com.hoo.aoo.file.domain.exception.FileExtensionMismatchException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BasicFileIdCreateStrategy implements FileIdCreateStrategy{

    private final String baseDir;
    private final Authority authority;
    private final FileType fileType;

    @Override
    public FileId create(String originalFilename, String fileSystemName) throws FileExtensionMismatchException {
        return FileId.create(baseDir, authority, fileType, originalFilename, fileSystemName);
    }
}
