package com.hoo.aoo.file.application.port.in;

public record DownloadImageResult(
        String disposition,
        byte[] bytes
) {
}
