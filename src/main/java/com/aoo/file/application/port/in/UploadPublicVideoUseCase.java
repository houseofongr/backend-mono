package com.aoo.file.application.port.in;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadPublicVideoUseCase {
    UploadFileResult publicUpload(List<MultipartFile> videos);
}
