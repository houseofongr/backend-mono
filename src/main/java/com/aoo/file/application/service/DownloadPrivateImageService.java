package com.aoo.file.application.service;

import com.aoo.common.domain.Authority;
import com.aoo.file.application.port.in.DownloadFileResult;
import com.aoo.file.application.port.in.DownloadImageUseCase;
import com.aoo.file.application.port.in.DownloadPrivateImageUseCase;
import com.aoo.file.application.port.out.jwt.VerifyAccessTokenPort;
import com.aoo.file.domain.FileType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DownloadPrivateImageService implements DownloadPrivateImageUseCase {

    private final VerifyAccessTokenPort verifyAccessTokenPort;
    private final DownloadImageUseCase downloadImageUseCase;

    @Override
    public DownloadFileResult privateDownload(String accessToken, Long fileId) {

        try {
            verifyAccessTokenPort.verifyImageToken(accessToken);
        } catch (JwtException e) {
            log.error("JWT Parse Exception : {}", e.getMessage());
            throw new FileException(FileErrorCode.INVALID_AUTHORITY);
        }

        return downloadImageUseCase.privateDownload(fileId);
    }
}
