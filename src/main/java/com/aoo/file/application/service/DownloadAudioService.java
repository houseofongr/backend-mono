package com.aoo.file.application.service;

import com.aoo.common.domain.Authority;
import com.aoo.file.application.port.in.DownloadFileResult;
import com.aoo.file.application.port.in.DownloadPrivateAudioUseCase;
import com.aoo.file.application.port.in.DownloadPublicAudioUseCase;
import com.aoo.file.domain.FileType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DownloadAudioService implements DownloadPublicAudioUseCase, DownloadPrivateAudioUseCase {

    private final DownloadService downloadService;

    @Override
    public DownloadFileResult publicDownload(Long fileId, boolean attachment) {
        return downloadService.load(fileId, FileType.AUDIO, Authority.PUBLIC_FILE_ACCESS, attachment);
    }

    @Override
    public DownloadFileResult privateDownload(Long fileId) {
        return downloadService.load(fileId, FileType.AUDIO, Authority.ALL_PRIVATE_AUDIO_ACCESS, false);
    }

}
