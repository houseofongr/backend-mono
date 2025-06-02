package com.aoo.file.application.port.in;

public interface DownloadPublicAudioUseCase {
    DownloadFileResult publicDownloadInline(Long fileId);
    DownloadFileResult publicDownloadAttachment(Long fileId);
}
