package com.hoo.aoo.file.application.service;

import com.hoo.aoo.file.application.port.in.UploadImageResult;
import com.hoo.aoo.file.application.port.in.UploadImageUseCase;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class UploadImageService implements UploadImageUseCase {

    @Override
    public UploadImageResult upload(List<MultipartFile> images) {
        return null;
    }
}