package com.hoo.aoo.file.domain;

import com.hoo.aoo.common.domain.Authority;
import com.hoo.aoo.file.domain.exception.FileSizeLimitExceedException;
import lombok.Getter;

import java.io.FileNotFoundException;

@Getter
public class File {
    private final FileId fileId;
    private final FileType type;
    private final FileStatus status;
    private final Owner owner;
    private final Authority authority;
    private final FileSize size;
    private java.io.File javaFile;

    public File(FileId fileId, FileType type, FileStatus status, Owner owner, Authority authority, FileSize size) {
        this.fileId = fileId;
        this.type = type;
        this.status = status;
        this.owner = owner;
        this.authority = authority;
        this.size = size;
    }

    public static File createImageFile(FileId fileId, Long size) throws FileSizeLimitExceedException, FileNotFoundException {

        FileSize fileSize = new FileSize(size);

        verifyExtension(FileType.IMAGE, fileId.getFileName());

        return new File(fileId, FileType.IMAGE, FileStatus.CREATED, Owner.empty(), Authority.PUBLIC, fileSize);
    }

    public static void verifyExtension(FileType fileType, String fileName) {
        switch (fileType) {
            case IMAGE -> {
                fileName.matches("^(?i)\\.(?:png|jpe?g|svg|gif)$");
            }
            case AUDIO, VIDEO -> {
                throw new UnsupportedOperationException();
            }
        }
    }

    public void retrieve() throws FileNotFoundException {
        if (javaFile == null)
            this.javaFile = new java.io.File(fileId.getPath());

        if (!javaFile.getParentFile().exists())
            throw new FileNotFoundException("Directory not found.");
    }
}
