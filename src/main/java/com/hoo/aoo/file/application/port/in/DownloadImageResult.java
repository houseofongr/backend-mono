package com.hoo.aoo.file.application.port.in;

import org.springframework.core.io.UrlResource;

public record DownloadImageResult(
        String disposition,
        UrlResource resource
) {
}
