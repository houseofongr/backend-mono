package com.aoo.file.application.service;

import com.aoo.common.domain.Authority;
import com.aoo.file.application.port.in.UploadFileResult;
import com.aoo.file.application.port.in.UploadPrivateImageUseCase;
import com.aoo.file.application.port.in.UploadPublicImageUseCase;
import com.aoo.file.domain.FileType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UploadImageService implements UploadPublicImageUseCase, UploadPrivateImageUseCase {

    private final FileProperties fileProperties;
    private final UploadService uploadService;

    @Override
    public UploadFileResult publicUpload(List<MultipartFile> images) {
        return uploadService.upload(images, new BasicFileIdCreateStrategy(fileProperties.getBaseDir(), Authority.PUBLIC_FILE_ACCESS, FileType.IMAGE));
    }

    @Override
    public UploadFileResult.FileInfo publicUpload(MultipartFile image) {
        return uploadService.upload(image, new BasicFileIdCreateStrategy(fileProperties.getBaseDir(), Authority.PUBLIC_FILE_ACCESS, FileType.IMAGE));
    }

    @Override
    public UploadFileResult privateUpload(List<MultipartFile> images) {
        return uploadService.upload(images, new BasicFileIdCreateStrategy(fileProperties.getBaseDir(), Authority.ALL_PRIVATE_IMAGE_ACCESS, FileType.IMAGE));
    }

}