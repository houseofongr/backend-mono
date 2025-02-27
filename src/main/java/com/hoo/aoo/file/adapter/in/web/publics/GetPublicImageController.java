package com.hoo.aoo.file.adapter.in.web.publics;

import com.hoo.aoo.file.application.port.in.DownloadFileResult;
import com.hoo.aoo.file.application.port.in.DownloadPublicImageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetPublicImageController {

    private final DownloadPublicImageUseCase downloadPublicImageUseCase;

    @GetMapping("/public/images/{fileId}")
    public ResponseEntity<?> download(@PathVariable Long fileId) {

        DownloadFileResult result = downloadPublicImageUseCase.publicDownload(fileId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, result.disposition())
                .contentType(result.mediaType())
                .body(result.resource());
    }

}
