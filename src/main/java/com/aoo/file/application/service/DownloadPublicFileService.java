package com.aoo.file.application.service;

import com.aoo.common.domain.Authority;
import com.aoo.file.application.port.in.DownloadFileResult;
import com.aoo.file.application.port.in.DownloadPublicFileUseCase;
import com.aoo.file.application.port.out.database.FindFilePort;
import com.aoo.file.domain.FileType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DownloadPublicFileService implements DownloadPublicFileUseCase {

    private final FindFilePort findFilePort;
    private final DownloadService downloadService;

    @Override
    public DownloadFileResult publicDownload(Long fileId, boolean attachment) {
        FileType fileType = findFilePort.findFileType(fileId);
        return downloadService.load(fileId, fileType, Authority.PUBLIC_FILE_ACCESS, attachment);
    }
}
