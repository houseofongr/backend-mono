package com.hoo.aoo.file.application.port.out.database;

import com.hoo.aoo.file.domain.File;

import java.util.Optional;

public interface
FindFilePort {
    Optional<File> load(Long fileId);
}
