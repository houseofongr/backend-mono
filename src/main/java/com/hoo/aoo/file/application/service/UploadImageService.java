package com.hoo.aoo.file.application.service;

import com.hoo.aoo.common.domain.Authority;
import com.hoo.aoo.file.adapter.out.filesystem.FileAttribute;
import com.hoo.aoo.file.application.port.in.UploadFileResult;
import com.hoo.aoo.file.application.port.in.UploadPrivateImageUseCase;
import com.hoo.aoo.file.application.port.in.UploadPublicImageUseCase;
import com.hoo.aoo.file.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UploadImageService implements UploadPublicImageUseCase, UploadPrivateImageUseCase {

    private final FileAttribute fileAttribute;
    private final UploadService uploadService;

    @Override
    @Transactional
    public UploadFileResult publicUpload(List<MultipartFile> images) {
        return uploadService.upload(images, new BasicFileIdCreateStrategy(fileAttribute.getBaseDir(), Authority.PUBLIC_FILE_ACCESS, FileType.IMAGE));
    }

    @Override
    @Transactional
    public UploadFileResult privateUpload(List<MultipartFile> images) {
        return uploadService.upload(images, new BasicFileIdCreateStrategy(fileAttribute.getBaseDir(), Authority.PRIVATE_FILE_ACCESS, FileType.IMAGE));
    }

}