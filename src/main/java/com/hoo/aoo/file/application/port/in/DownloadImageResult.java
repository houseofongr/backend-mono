package com.hoo.aoo.file.application.port.in;

import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;

public record DownloadImageResult(
        String disposition,
        MediaType mediaType,
        UrlResource resource
) {
}
