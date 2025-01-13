package com.hoo.aoo.file.application.service;

import com.hoo.aoo.file.application.port.in.DownloadImageResult;
import com.hoo.aoo.file.application.port.in.DownloadPublicImageUseCase;
import com.hoo.aoo.file.application.port.out.database.LoadImageFilePort;
import com.hoo.aoo.file.domain.File;
import com.hoo.aoo.file.domain.exception.FileExtensionMismatchException;
import com.hoo.aoo.file.domain.exception.FileSizeLimitExceedException;
import com.hoo.aoo.file.domain.exception.IllegalFileAuthorityDirException;
import com.hoo.aoo.file.domain.exception.IllegalFileTypeDirException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ContentDisposition;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;

@Slf4j
@Component
@RequiredArgsConstructor
public class DownloadPublicImageService implements DownloadPublicImageUseCase {

    private final LoadImageFilePort loadImageFilePort;

    @Override
    public DownloadImageResult download(Long fileId) {
        try {
            File loadedFile = loadImageFilePort.load(fileId)
                    .orElseThrow(() -> new FileException(FileErrorCode.FILE_NOT_FOUND));

            java.io.File javaFile = new java.io.File(loadedFile.getFileId().getPath());

            ContentDisposition disposition = ContentDisposition.inline()
                    .filename(loadedFile.getFileId().getFileSystemName())
                    .build();

            return new DownloadImageResult(
                    disposition.toString(),
                    Files.readAllBytes(javaFile.toPath()));

        } catch (IOException e) {
            log.error(e.getMessage());
            throw new FileException(FileErrorCode.RETRIEVE_FILE_FAILED);
        } catch (FileSizeLimitExceedException e) {
            log.error(e.getMessage());
            throw new FileException(FileErrorCode.FILE_SIZE_LIMIT_EXCEED);
        } catch (FileExtensionMismatchException e) {
            log.error(e.getMessage());
            throw new FileException(FileErrorCode.INVALID_FILE_EXTENSION);
        } catch (IllegalFileTypeDirException e) {
            log.error(e.getMessage());
            throw new FileException(FileErrorCode.ILLEGAL_FILE_TYPE_DIR);
        } catch (IllegalFileAuthorityDirException e) {
            log.error(e.getMessage());
            throw new FileException(FileErrorCode.ILLEGAL_FILE_AUTHORITY_DIR);
        }
    }
}
