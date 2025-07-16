package com.hoo.file.adapter.in.web.publics;

import com.hoo.file.application.port.in.UploadFileResult;
import com.hoo.file.application.port.in.UploadPublicVideoUseCase;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostPublicVideoController {

    private final UploadPublicVideoUseCase uploadPublicVideoUseCase;

    @PostMapping("/public/videos")
    public ResponseEntity<UploadFileResult> upload(@NotEmpty @RequestParam List<MultipartFile> videos) {
        UploadFileResult response = uploadPublicVideoUseCase.publicUpload(videos);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
