package com.hoo.aoo.file.domain;

import com.hoo.aoo.file.domain.exception.FileSizeLimitExceedException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FileF {
    IMAGE_FILE_1(FileType.IMAGE, "test.png", 1234L);

    private final FileType fileType;
    private final String fileName;
    private final Long size;

    public File get(String directory) {
        switch (fileType) {
            case IMAGE -> {
                try {
                    return File.createImageFile(new FileId(directory, fileName), size, null);
                } catch (FileSizeLimitExceedException e) {
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
