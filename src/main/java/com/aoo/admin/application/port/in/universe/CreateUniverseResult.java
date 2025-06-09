package com.aoo.admin.application.port.in.universe;

import java.util.List;

public record CreateUniverseResult(
        String message,
        Long universeId,
        Long thumbMusicId,
        Long thumbnailId,
        Long innerImageId,
        Long authorId,
        Long createdTime,
        String title,
        String description,
        String author,
        String category,
        String publicStatus,
        List<String> hashtags
) {
}
