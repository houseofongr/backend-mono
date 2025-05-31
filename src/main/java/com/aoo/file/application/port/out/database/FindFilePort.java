package com.aoo.file.application.port.out.database;

import com.aoo.file.domain.File;

import java.util.Optional;

public interface
FindFilePort {
    Optional<File> load(Long fileId);
}
