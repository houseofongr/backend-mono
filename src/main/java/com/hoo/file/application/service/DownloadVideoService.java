package com.hoo.file.application.service;

import com.hoo.common.domain.Authority;
import com.hoo.file.application.port.in.DownloadFileResult;
import com.hoo.file.application.port.in.DownloadPublicVideoUseCase;
import com.hoo.file.domain.FileType;
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
