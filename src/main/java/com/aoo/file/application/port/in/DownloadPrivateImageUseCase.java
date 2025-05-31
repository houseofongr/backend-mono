package com.aoo.file.application.port.in;

public interface DownloadPrivateImageUseCase {

    DownloadFileResult privateDownload(String accessToken, Long fileId);
}
