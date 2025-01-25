package com.hoo.aoo.file.domain;

import com.hoo.aoo.common.domain.Authority;
import com.hoo.aoo.file.domain.exception.FileExtensionMismatchException;
import com.hoo.aoo.file.domain.exception.FileSizeLimitExceedException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FileF {
    IMAGE_FILE_1(FileType.IMAGE, "test.png", "test-1234.png", 1234L);

    private final FileType fileType;
    private final String realFileName;
    private final String fileSystemName;
    private final Long size;

    public File get(String baseDir) {
        switch (fileType) {
            case IMAGE -> {
                try {
                    return File.create(FileId.create(baseDir, Authority.PUBLIC_FILE_ACCESS, FileType.IMAGE, realFileName, fileSystemName), FileStatus.CREATED, Owner.empty(), new FileSize(size, 100 * 1024 * 1024L));
                } catch (FileSizeLimitExceedException | FileExtensionMismatchException e) {
                    throw new RuntimeException(e);
                }
            }
            case AUDIO, VIDEO -> {
                throw new UnsupportedOperationException();
            }
        }
        throw new UnsupportedOperationException();
    }
}
