package com.hoo.aoo.file.domain;

import lombok.Getter;

@Getter
public class FileId {
    private final String directory;
    private final String fileName;

    public FileId(String directory, String fileName) {

        if (directory.charAt(directory.length() - 1) == '/')
            directory = directory.substring(0, directory.length() - 1);

        this.directory = directory;
        this.fileName = fileName;
    }

    public String getPath() {
        return directory + "/" + fileName;
    }
}
