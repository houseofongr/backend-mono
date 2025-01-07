package com.hoo.aoo.file.adapter.in.web.publics;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.print.attribute.standard.Media;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@RequiredArgsConstructor
public class ImageDownloadController {

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
