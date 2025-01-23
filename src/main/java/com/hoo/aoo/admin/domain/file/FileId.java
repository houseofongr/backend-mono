package com.hoo.aoo.admin.domain.file;

import lombok.Getter;

@Getter
public class FileId {
    private final Long id;

    public FileId(Long id) {
        this.id = id;
    }
}
