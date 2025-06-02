package com.aoo.file.adapter.in.web.publics;

import com.aoo.file.application.port.in.DownloadFileResult;
import com.aoo.file.application.port.in.DownloadPublicAudioUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetPublicAudioController {

    private final DownloadPublicAudioUseCase downloadPublicAudioUseCase;

    @GetMapping("/public/audios/{fileId}")
    public ResponseEntity<?> downloadInline(@PathVariable Long fileId) {

        DownloadFileResult result = downloadPublicAudioUseCase.publicDownloadInline(fileId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, result.disposition())
                .contentType(result.mediaType())
                .body(result.resource());
    }

    @GetMapping("/public/audios/attachment/{fileId}")
    public ResponseEntity<?> downloadAttachment(@PathVariable Long fileId) {

        DownloadFileResult result = downloadPublicAudioUseCase.publicDownloadAttachment(fileId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, result.disposition())
                .contentType(result.mediaType())
                .body(result.resource());
    }

}
