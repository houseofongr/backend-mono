package com.hoo.aoo.file.application.service;

import com.hoo.aoo.file.application.port.in.DownloadImageResult;
import com.hoo.aoo.file.application.port.in.DownloadImageUseCase;
import com.hoo.aoo.file.application.port.out.database.LoadPublicImageFilePersistencePort;
import com.hoo.aoo.file.domain.File;
import com.hoo.aoo.file.domain.exception.FileSizeLimitExceedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;

@Component
@RequiredArgsConstructor
public class DownloadImageService implements DownloadImageUseCase {

    private final LoadPublicImageFilePersistencePort loadPublicImageFilePersistencePort;

    @Override
    public DownloadImageResult download(Long fileId) {
        try {
            File loadedFile = loadPublicImageFilePersistencePort.load(fileId)
                    .orElseThrow(() -> new FileException(FileErrorCode.FILE_NOT_FOUND));

            loadedFile.retrieve();

            ContentDisposition disposition = ContentDisposition.inline()
                    .filename(loadedFile.getFileId().getFileName())
                    .build();

            return new DownloadImageResult(
                    disposition.toString(),
                    Files.readAllBytes(loadedFile.getJavaFile().toPath()));

        } catch (IOException e) {

            throw new FileException(FileErrorCode.RETRIEVE_FILE_FAILED);

        } catch (FileSizeLimitExceedException e) {

            throw new FileException(FileErrorCode.FILE_SIZE_LIMIT_EXCEED);
        }
    }
}
