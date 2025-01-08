package com.hoo.aoo.file.application.port.out.database;

import com.hoo.aoo.file.domain.File;
import com.hoo.aoo.file.domain.exception.FileSizeLimitExceedException;

import java.util.Optional;

public interface LoadPublicImageFilePersistencePort {
    Optional<File> load(Long fileId) throws FileSizeLimitExceedException;
}
