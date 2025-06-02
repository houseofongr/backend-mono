package com.aoo.file.adapter.in.web.publics;

import com.aoo.file.application.port.in.DownloadFileResult;
import com.aoo.file.application.port.in.DownloadPublicImageUseCase;
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
    public ResponseEntity<?> downloadInline(@PathVariable Long fileId) {

        DownloadFileResult result = downloadPublicImageUseCase.publicDownloadInline(fileId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, result.disposition())
                .contentType(result.mediaType())
                .body(result.resource());
    }

    @GetMapping("/public/images/attachment/{fileId}")
    public ResponseEntity<?> downloadAttachment(@PathVariable Long fileId) {

        DownloadFileResult result = downloadPublicImageUseCase.publicDownloadAttachment(fileId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, result.disposition())
                .contentType(result.mediaType())
                .body(result.resource());
    }

}
