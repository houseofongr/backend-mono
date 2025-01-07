package com.hoo.aoo.file.adapter.in.web.publics;

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

    @GetMapping("/file/public/images/{fileId}")
    public ResponseEntity<?> download(@PathVariable Long fileId) throws IOException {

        Resource classPathResource = new ClassPathResource("logo.png");

        byte[] bytes = classPathResource.getContentAsByteArray();

        ContentDisposition disposition = ContentDisposition.attachment()
                .filename("result.png", StandardCharsets.UTF_8)
                .build();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, disposition.toString()).body(bytes);

    }

}
