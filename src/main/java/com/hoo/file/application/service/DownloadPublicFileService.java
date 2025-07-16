package com.hoo.file.application.service;

import com.hoo.common.domain.Authority;
import com.hoo.file.application.port.in.DownloadFileResult;
import com.hoo.file.application.port.in.DownloadPublicFileUseCase;
import com.hoo.file.application.port.out.database.FindFilePort;
import com.hoo.file.domain.FileType;
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
