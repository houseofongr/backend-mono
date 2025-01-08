package com.hoo.aoo.file.adapter.in.web.publics;

import com.hoo.aoo.file.application.port.in.DownloadImageResult;
import com.hoo.aoo.file.application.port.in.DownloadImageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


@RestController
@RequiredArgsConstructor
public class DownloadImageController {

    private final DownloadImageUseCase downloadImageUseCase;

    @GetMapping("/public/images/{fileId}")
    public ResponseEntity<?> download(@PathVariable Long fileId) {

        DownloadImageResult result = downloadImageUseCase.download(fileId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, result.disposition())
                .body(result.bytes());
    }

}
