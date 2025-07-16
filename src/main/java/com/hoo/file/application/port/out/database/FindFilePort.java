package com.hoo.file.application.port.out.database;

import com.hoo.file.domain.File;
import com.hoo.file.domain.FileType;

import java.util.List;
import java.util.Optional;

public interface
FindFilePort {
    Optional<File> load(Long fileId);
    List<File> loadAll(List<Long> ids);
    FileType findFileType(Long fileId);
}
