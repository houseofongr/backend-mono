package com.hoo.aoo.file.adapter.in.web.privates;

import com.hoo.aoo.file.application.port.in.DownloadImageResult;
import com.hoo.aoo.file.application.port.in.DownloadPrivateImageUseCase;
import com.hoo.aoo.file.domain.File;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetPrivateImageController {

    private final DownloadPrivateImageUseCase downloadPrivateImageUseCase;

    @GetMapping("/private/images/{fileId}")
    public ResponseEntity<?> download(@PathVariable Long fileId) {

        DownloadImageResult result = downloadPrivateImageUseCase.privateDownload(fileId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, result.disposition())
                .contentType(result.mediaType())
                .body(result.resource());
    }

}

