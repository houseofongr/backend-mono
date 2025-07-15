package com.aoo.file.application.service;

import com.aoo.common.domain.Authority;
import com.aoo.file.application.port.in.DownloadFileResult;
import com.aoo.file.application.port.in.DownloadPublicVideoUseCase;
import com.aoo.file.domain.FileType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DownloadVideoService implements DownloadPublicVideoUseCase {

    private final DownloadService downloadService;

    @Override
    public DownloadFileResult publicDownload(Long fileId, boolean attachment) {
        return downloadService.load(fileId, FileType.VIDEO, Authority.PUBLIC_FILE_ACCESS, attachment);
    }
}
