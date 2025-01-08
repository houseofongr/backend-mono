package com.hoo.aoo.file.domain;

import com.hoo.aoo.common.domain.Authority;
import com.hoo.aoo.file.domain.exception.FileSizeLimitExceedException;
import lombok.Getter;

@Getter
public class File {
    private final FileId fileId;
    private final FileType type;
    private final FileStatus status;
    private final Owner owner;
    private final Authority authority;
    private final FileSize size;
    private final java.io.File javaFile;

    public File(FileId fileId, FileType type, FileStatus status, Owner owner, Authority authority, FileSize size, java.io.File javaFile) {
        this.fileId = fileId;
        this.type = type;
        this.status = status;
        this.owner = owner;
        this.authority = authority;
        this.size = size;
        this.javaFile = javaFile;
    }

    public static File createImageFile(FileId fileId, Long size, java.io.File javaFile) throws FileSizeLimitExceedException {

        FileSize fileSize = new FileSize(size);

        verifyExtension(FileType.IMAGE, fileId.getFileName());

        return new File(fileId, FileType.IMAGE, FileStatus.CREATED, Owner.empty(), Authority.PUBLIC, fileSize, javaFile);
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
}
