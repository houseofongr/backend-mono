package com.hoo.aoo.file.application.service;

import com.hoo.aoo.common.domain.Authority;
import com.hoo.aoo.file.application.port.in.DownloadFileResult;
import com.hoo.aoo.file.application.port.in.DownloadPrivateImageUseCase;
import com.hoo.aoo.file.application.port.in.DownloadPublicImageUseCase;
import com.hoo.aoo.file.application.port.out.database.FindImageFilePort;
import com.hoo.aoo.file.domain.File;
import com.hoo.aoo.file.domain.exception.FileExtensionMismatchException;
import com.hoo.aoo.file.domain.exception.FileSizeLimitExceedException;
import com.hoo.aoo.file.domain.exception.IllegalFileAuthorityDirException;
import com.hoo.aoo.file.domain.exception.IllegalFileTypeDirException;
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
public class DownloadImageService implements DownloadPublicImageUseCase, DownloadPrivateImageUseCase {

    private final FindImageFilePort findImageFilePort;

    @Override
    @Transactional(readOnly = true)
    public DownloadFileResult privateDownload(Long fileId) {
        return download(fileId, Authority.PRIVATE_FILE_ACCESS);
    }

    @Override
    @Transactional(readOnly = true)
    public DownloadFileResult publicDownload(Long fileId) {
        return download(fileId, Authority.PUBLIC_FILE_ACCESS);
    }

    private DownloadFileResult download(Long fileId, Authority authority) {
        try {
            File loadedFile = findImageFilePort.find(fileId)
                    .orElseThrow(() -> new FileException(FileErrorCode.FILE_NOT_FOUND));

            if (loadedFile.getFileId().getAuthority() != authority)
                throw new FileException(FileErrorCode.INVALID_AUTHORITY);

            ContentDisposition disposition = ContentDisposition.inline()
                    .filename(loadedFile.getFileId().getFileSystemName())
                    .build();

            String filePath = loadedFile.getFileId().getFilePath();
            return new DownloadFileResult(
                    disposition.toString(),
                    MediaType.parseMediaType(Files.probeContentType(Path.of(filePath))),
                    new UrlResource(filePath));

        } catch (IOException e) {
            throw new FileException(FileErrorCode.RETRIEVE_FILE_FAILED);

        } catch (FileSizeLimitExceedException e) {
            throw new FileException(FileErrorCode.FILE_SIZE_LIMIT_EXCEED);

        } catch (FileExtensionMismatchException e) {
            throw new FileException(FileErrorCode.INVALID_FILE_EXTENSION);

        } catch (IllegalFileTypeDirException e) {
            throw new FileException(FileErrorCode.ILLEGAL_FILE_TYPE_DIR);

        } catch (IllegalFileAuthorityDirException e) {
            throw new FileException(FileErrorCode.ILLEGAL_FILE_AUTHORITY_DIR);

        }
    }
}
