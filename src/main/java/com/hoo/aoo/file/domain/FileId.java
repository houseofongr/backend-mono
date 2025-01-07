package com.hoo.aoo.file.domain;

import lombok.Getter;

@Getter
public class FileId {
    public static final String BASE_IMAGE_FILE_PATH = "/archive/images/";
    public static final String BASE_AUDIO_FILE_PATH = "/archive/audios/";
    public static final String BASE_VIDEO_FILE_PATH = "/archive/videos/";

    private final String directory;
    private final String fileName;

    public FileId(FileType type, String fileName) {
        this.fileName = fileName;
        switch (type) {
            case IMAGE -> this.directory = BASE_IMAGE_FILE_PATH;
            case AUDIO -> this.directory = BASE_AUDIO_FILE_PATH;
            case VIDEO -> this.directory = BASE_VIDEO_FILE_PATH;
            default -> throw new UnsupportedOperationException();
        }
    }
}
