package com.hoo.aoo.file.application.service;

import com.hoo.aoo.common.domain.Authority;
import com.hoo.aoo.file.adapter.out.filesystem.FileAttribute;
import com.hoo.aoo.file.application.port.in.UploadFileResult;
import com.hoo.aoo.file.application.port.in.UploadPrivateAudioUseCase;
import com.hoo.aoo.file.domain.FileType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UploadAudioService implements UploadPrivateAudioUseCase {

    private final FileAttribute fileAttribute;
    private final UploadService uploadService;


    @Override
    @Transactional
    public UploadFileResult privateUpload(List<MultipartFile> audios, Long ownerId) {
        return uploadService.upload(audios, ownerId, new BasicFileIdCreateStrategy(fileAttribute.getBaseDir(), Authority.PRIVATE_FILE_ACCESS, FileType.IMAGE));
    }

    @Override
    @Transactional
    public UploadFileResult privateUpload(MultipartFile audios, Long ownerId) {
        return privateUpload(List.of(audios), ownerId);
    }
}
