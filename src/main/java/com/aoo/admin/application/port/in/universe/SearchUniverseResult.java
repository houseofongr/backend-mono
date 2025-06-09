package com.aoo.admin.application.port.in.universe;

import com.aoo.common.application.port.in.Pagination;

import java.util.List;

public record SearchUniverseResult(
        List<UniverseListInfo> universes,
        Pagination pagination
) {
    public record UniverseListInfo(
            Long id,
            Long thumbnailId,
            Long thumbMusicId,
            Long authorId,
            Long createdTime,
            Long updatedTime,
            Long view,
            Integer like,
            String title,
            String description,
            String author,
            String category,
            String publicStatus,
            List<String> hashtags
    ) {

    }

    public record UniverseDetailInfo(
            Long id,
            Long thumbMusicId,
            Long thumbnailId,
            Long innerImageId,
            Long authorId,
            Long createdTime,
            Long updatedTime,
            Long view,
            Integer like,
            String title,
            String description,
            String author,
            String category,
            String publicStatus,
            List<String> hashtags
    ) {
    }
}
