package com.hoo.aoo.file.application.service;

import com.hoo.aoo.file.application.port.in.DownloadFileResult;
import com.hoo.aoo.file.application.port.in.DownloadPrivateAudioUseCase;
import org.springframework.stereotype.Service;

@Service
public class DownloadAudioService implements DownloadPrivateAudioUseCase {

    @Override
    public DownloadFileResult privateDownload(Long fileId) {
        return null;
    }
}
