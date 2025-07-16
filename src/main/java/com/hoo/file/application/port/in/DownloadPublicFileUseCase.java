package com.hoo.file.application.port.in;

public interface DownloadPublicFileUseCase {
    DownloadFileResult publicDownload(Long fileId, boolean attachment);
}
