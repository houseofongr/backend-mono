package com.aoo.file.application.port.in;

public interface DownloadPublicVideoUseCase {
    DownloadFileResult publicDownload(Long fileId, boolean attachment);
}
