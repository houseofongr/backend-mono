package com.hoo.aoo.file.domain;

import com.hoo.aoo.common.domain.Authority;

public class File {
    private final Path path;
    private final FileType fileType;
    private final FileStatus status;
    private final Owner owner;
    private final Authority authority;

    public File(Path path, FileType fileType, FileStatus status, Owner owner, Authority authority) {
        this.path = path;
        this.fileType = fileType;
        this.status = status;
        this.owner = owner;
        this.authority = authority;
    }
}
