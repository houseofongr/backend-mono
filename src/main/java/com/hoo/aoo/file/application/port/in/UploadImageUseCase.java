package com.hoo.aoo.file.application.port.in;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadImageUseCase {
    UploadImageResult upload(List<MultipartFile> images);
}
