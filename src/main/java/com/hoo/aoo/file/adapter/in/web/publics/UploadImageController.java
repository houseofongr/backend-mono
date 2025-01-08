package com.hoo.aoo.file.adapter.in.web.publics;

import com.hoo.aoo.file.application.port.in.UploadImageResult;
import com.hoo.aoo.file.application.port.in.UploadImageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UploadImageController {

    private final UploadImageUseCase uploadImageUseCase;

    @PostMapping(value = "/public/images")
    public ResponseEntity<UploadImageResult> upload(@RequestParam("images") List<MultipartFile> images) {
        UploadImageResult response = uploadImageUseCase.upload(images);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
