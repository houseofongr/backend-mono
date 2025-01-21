package com.hoo.aoo.file.adapter.in.web.privates;

import com.hoo.aoo.file.application.port.in.DownloadPrivateAudioUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.net.MalformedURLException;
import java.nio.file.Files;

@RestController
@RequiredArgsConstructor
public class GetPrivateAudioController {

    private final DownloadPrivateAudioUseCase downloadPrivateAudioUseCase;

    @GetMapping("/private/audios/{fileId}")
    public ResponseEntity<?> download(){

        ContentDisposition disposition = ContentDisposition.inline()
                .filename("sample.mp3")
                .build();

        ClassPathResource resource = new ClassPathResource("private/audios/sample.mp3");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, disposition.toString())
                .contentType(MediaType.parseMediaType("audio/mp3"))
                .body(resource);
    }
}
