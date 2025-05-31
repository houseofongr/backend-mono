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

    private final FindFilePort findFilePort;

    @Override
    public DownloadFileResult publicDownload(Long fileId) {
        return download(fileId, Authority.PUBLIC_FILE_ACCESS);
    }

    @Override
    public DownloadFileResult privateDownload(Long fileId) {
        return download(fileId,Authority.ALL_PRIVATE_AUDIO_ACCESS);
    }

    private DownloadFileResult download(Long fileId, Authority authority) {
        try {

            File file = findFilePort.load(fileId)
                    .orElseThrow(() -> new FileException(FileErrorCode.FILE_NOT_FOUND));

            FileId audioFileId = file.getFileId();

            if (audioFileId.getAuthority() != authority)
                throw new FileException(FileErrorCode.INVALID_AUTHORITY);

            if (audioFileId.getFileType() != FileType.AUDIO)
                throw new FileException(FileErrorCode.INVALID_FILE_TYPE);

            ContentDisposition disposition = ContentDisposition.inline()
                    .filename(audioFileId.getFileSystemName())
                    .build();

            return new DownloadFileResult(
                    disposition.toString(),
                    MediaType.parseMediaType(Files.probeContentType(Path.of(audioFileId.getFilePath()))),
                    new UrlResource(audioFileId.getFilePath()));

        } catch (IOException e) {
            throw new FileException(e, FileErrorCode.RETRIEVE_FILE_FAILED);
        }
    }
}
