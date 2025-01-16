package com.hoo.aoo.file.application.port.out.database;

import com.hoo.aoo.file.domain.File;
import com.hoo.aoo.file.domain.exception.FileExtensionMismatchException;
import com.hoo.aoo.file.domain.exception.FileSizeLimitExceedException;
import com.hoo.aoo.file.domain.exception.IllegalFileAuthorityDirException;
import com.hoo.aoo.file.domain.exception.IllegalFileTypeDirException;

import java.io.FileNotFoundException;
import java.util.Optional;

public interface
FindImageFilePort {
    Optional<File> load(Long fileId) throws FileSizeLimitExceedException, FileNotFoundException, FileExtensionMismatchException, IllegalFileTypeDirException, IllegalFileAuthorityDirException;
}
