package com.hoo.aoo.file.application.port.in;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadPrivateAudioUseCase {
    UploadAudioResult privateUpload(List<MultipartFile> audios, Long ownerId);
    UploadAudioResult privateUpload(MultipartFile audios, Long ownerId);
}
