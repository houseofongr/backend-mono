package com.hoo.aoo.file.domain;

import lombok.Getter;

@Getter
public class Path {
    private final String directory;
    private final String fileName;

    public Path(String directory, String fileName) {
        this.directory = directory;
        this.fileName = fileName;
    }
}
