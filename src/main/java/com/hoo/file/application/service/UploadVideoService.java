package com.hoo.file.application.service;

import com.hoo.common.domain.Authority;
import com.hoo.file.application.port.in.UploadFileResult;
import com.hoo.file.application.port.in.UploadPublicVideoUseCase;
import com.hoo.file.domain.FileType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UploadVideoService implements UploadPublicVideoUseCase {

    private final FileProperties fileProperties;
    private final UploadService uploadService;

    @Override
    public UploadFileResult publicUpload(List<MultipartFile> videos) {
        return uploadService.upload(videos, new BasicFileIdCreateStrategy(fileProperties.getBaseDir(), Authority.PUBLIC_FILE_ACCESS, FileType.VIDEO));
    }
}
