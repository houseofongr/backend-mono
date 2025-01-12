package com.hoo.aoo.file.domain;

import lombok.Getter;

@Getter
public class File {
    private final FileId fileId;
    private final FileStatus status;
    private final Owner owner;
    private final FileSize size;

    private File(FileId fileId, FileStatus status, Owner owner, FileSize size) {
        this.fileId = fileId;
        this.status = status;
        this.owner = owner;
        this.size = size;
    }

    public static File create(FileId fileId, FileStatus fileStatus, Owner owner, FileSize size) {

        return new File(fileId, fileStatus, owner, size);
    }
}
