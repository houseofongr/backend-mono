package com.hoo.aoo.admin.domain.file;

public class File {
    
    private final FileId fileId;
    private final FileType fileType;

    public File(FileId fileId, FileType fileType) {
        this.fileId = fileId;
        this.fileType = fileType;
    }
}
