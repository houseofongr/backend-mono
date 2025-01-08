package com.hoo.aoo.file.adapter.out.filesystem;

import com.hoo.aoo.file.application.port.out.filesystem.StoreImageFileSystemPort;
import com.hoo.aoo.file.application.service.FileErrorCode;
import com.hoo.aoo.file.application.service.FileException;
import com.hoo.aoo.file.domain.File;
import com.hoo.aoo.file.domain.FileId;
import com.hoo.aoo.file.domain.exception.FileSizeLimitExceedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileSystemAdapter implements StoreImageFileSystemPort {

    private final FileAttribute fileAttribute;

    @Override
    public File storePublicFile(MultipartFile file) {
        FileId fileId = new FileId(fileAttribute.getPublicImagePath(), file.getOriginalFilename());

        java.io.File javaFile = new java.io.File(fileId.getPath());

        try {
            if (!javaFile.createNewFile())
                throw new FileException(FileErrorCode.FILE_NAME_DUPLICATION);

        } catch (IOException e) {

            log.error(e.getMessage());
            throw new FileException(FileErrorCode.NEW_FILE_CREATION_FAILED);
        }

        try {

            file.transferTo(javaFile);
            return File.createImageFile(fileId, file.getSize(), javaFile);

        } catch (IOException e) {
            log.error(e.getMessage());
            throw new FileException(FileErrorCode.NEW_FILE_CREATION_FAILED);

        } catch (FileSizeLimitExceedException e) {

            throw new FileException(FileErrorCode.FILE_SIZE_LIMIT_EXCEED);

        }
    }
}
