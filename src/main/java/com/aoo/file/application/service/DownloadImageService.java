package com.aoo.file.application.service;

import com.aoo.common.domain.Authority;
import com.aoo.file.application.port.in.DownloadFileResult;
import com.aoo.file.application.port.in.DownloadImageUseCase;
import com.aoo.file.application.port.in.DownloadPublicImageUseCase;
import com.aoo.file.application.port.out.database.FindFilePort;
import com.aoo.file.domain.File;
import com.aoo.file.domain.FileId;
import com.aoo.file.domain.FileType;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
@RequiredArgsConstructor
public class DownloadImageService implements DownloadPublicImageUseCase, DownloadImageUseCase {

    private final FindFilePort findFilePort;

    @Override
    @Transactional(readOnly = true)
    public DownloadFileResult privateDownload(Long fileId) {
        return download(fileId, Authority.ALL_PRIVATE_IMAGE_ACCESS);
    }

    @Override
    @Transactional(readOnly = true)
    public DownloadFileResult publicDownload(Long fileId) {
        return download(fileId, Authority.PUBLIC_FILE_ACCESS);
    }

    private DownloadFileResult download(Long fileId, Authority authority) {
        try {

            File loadedFile = findFilePort.load(fileId)
                    .orElseThrow(() -> new FileException(FileErrorCode.FILE_NOT_FOUND));

            FileId imageFileId = loadedFile.getFileId();

            if (imageFileId.getFileType() != FileType.IMAGE)
                throw new FileException(FileErrorCode.INVALID_FILE_TYPE);

            if (imageFileId.getAuthority() != authority)
                throw new FileException(FileErrorCode.INVALID_AUTHORITY);

            ContentDisposition disposition = ContentDisposition.inline()
                    .filename(imageFileId.getFileSystemName())
                    .build();

            return new DownloadFileResult(
                    disposition.toString(),
                    MediaType.parseMediaType(Files.probeContentType(Path.of(imageFileId.getFilePath()))),
                    new UrlResource(imageFileId.getFilePath()));

        } catch (IOException e) {

            throw new FileException(e, FileErrorCode.RETRIEVE_FILE_FAILED);

        }
    }
}
