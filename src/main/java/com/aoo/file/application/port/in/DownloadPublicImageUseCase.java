package com.aoo.file.application.port.in;

public interface DownloadPublicImageUseCase {
    DownloadFileResult publicDownloadInline(Long fileId);
    DownloadFileResult publicDownloadAttachment(Long fileId);
}
