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
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DownloadImageService implements DownloadPublicImageUseCase, DownloadImageUseCase {

    private final DownloadService downloadService;

    @Override
    public DownloadFileResult publicDownloadInline(Long fileId) {
        return downloadService.load(fileId, FileType.IMAGE, Authority.PUBLIC_FILE_ACCESS, false);
    }

    @Override
    public DownloadFileResult publicDownloadAttachment(Long fileId) {
        return downloadService.load(fileId, FileType.IMAGE, Authority.PUBLIC_FILE_ACCESS, true);
    }

    @Override
    public DownloadFileResult privateDownload(Long fileId) {
        return downloadService.load(fileId, FileType.IMAGE, Authority.ALL_PRIVATE_IMAGE_ACCESS, false);
    }
}
