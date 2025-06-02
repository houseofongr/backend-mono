package com.aoo.file.application.service;

import com.aoo.common.domain.Authority;
import com.aoo.file.application.port.in.DownloadFileResult;
import com.aoo.file.application.port.in.DownloadPrivateAudioUseCase;
import com.aoo.file.application.port.in.DownloadPublicAudioUseCase;
import com.aoo.file.application.port.out.database.FindFilePort;
import com.aoo.file.domain.File;
import com.aoo.file.domain.FileId;
import com.aoo.file.domain.FileType;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DownloadAudioService implements DownloadPublicAudioUseCase, DownloadPrivateAudioUseCase {

    private final DownloadService downloadService;

    @Override
    public DownloadFileResult publicDownloadInline(Long fileId) {
        return downloadService.load(fileId, FileType.AUDIO, Authority.PUBLIC_FILE_ACCESS, false);
    }

    @Override
    public DownloadFileResult publicDownloadAttachment(Long fileId) {
        return downloadService.load(fileId, FileType.AUDIO, Authority.PUBLIC_FILE_ACCESS, true);
    }

    @Override
    public DownloadFileResult privateDownload(Long fileId) {
        return downloadService.load(fileId, FileType.AUDIO, Authority.ALL_PRIVATE_AUDIO_ACCESS, false);
    }

}
