package com.hoo.aoo.file.domain;

import com.hoo.aoo.common.domain.Authority;
import lombok.Getter;

@Getter
public class File {
    private final FileId fileId;
    private final FileType type;
    private final FileStatus status;
    private final Owner owner;
    private final Authority authority;
    private final FileSize size;

    public File(FileId fileId, FileType type, FileStatus status, Owner owner, Authority authority, FileSize size) {
        this.fileId = fileId;
        this.type = type;
        this.status = status;
        this.owner = owner;
        this.authority = authority;
        this.size = size;
    }

    public static File createImageFile(String fileName, Long size) {

        FileId fileId = new FileId(FileType.IMAGE, fileName);
        FileSize fileSize = new FileSize(size);

        return new File(fileId, FileType.IMAGE, FileStatus.CREATED, Owner.empty(), Authority.PUBLIC, fileSize);
    }
}
