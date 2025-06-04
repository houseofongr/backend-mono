package com.aoo.admin.application.port.in.universe;

import com.aoo.common.adapter.out.persistence.entity.UniverseJpaEntity;
import com.aoo.common.application.port.in.Pagination;

import java.util.List;

public record SearchUniverseResult(
        List<UniverseInfo> universes,
        Pagination pagination
) {
    public record UniverseInfo(
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

        public static UniverseInfo of(UniverseJpaEntity universeJpaEntity) {
            return new UniverseInfo(
                    universeJpaEntity.getId(),
                    universeJpaEntity.getThumbnailFileId(),
                    universeJpaEntity.getThumbMusicFileId(),
                    universeJpaEntity.getAuthor().getId(),
                    universeJpaEntity.getCreatedTime().toEpochSecond(),
                    universeJpaEntity.getUpdatedTime().toEpochSecond(),
                    universeJpaEntity.getViewCount(),
                    universeJpaEntity.getUniverseLikes().size(),
                    universeJpaEntity.getTitle(),
                    universeJpaEntity.getDescription(),
                    universeJpaEntity.getAuthor().getNickname(),
                    universeJpaEntity.getCategory().name(),
                    universeJpaEntity.getPublicStatus().name(),
                    universeJpaEntity.getUniverseHashtags().stream()
                            .map(universeHashtagJpaEntity -> universeHashtagJpaEntity.getHashtag().getTag())
                            .toList()
            );
        }
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

        public static UniverseDetailInfo of(UniverseJpaEntity universeJpaEntity) {
            return new UniverseDetailInfo(
                    universeJpaEntity.getId(),
                    universeJpaEntity.getThumbMusicFileId(),
                    universeJpaEntity.getThumbnailFileId(),
                    universeJpaEntity.getInnerImageFileId(),
                    universeJpaEntity.getAuthor().getId(),
                    universeJpaEntity.getCreatedTime().toEpochSecond(),
                    universeJpaEntity.getUpdatedTime().toEpochSecond(),
                    universeJpaEntity.getViewCount(),
                    universeJpaEntity.getUniverseLikes().size(),
                    universeJpaEntity.getTitle(),
                    universeJpaEntity.getDescription(),
                    universeJpaEntity.getAuthor().getNickname(),
                    universeJpaEntity.getCategory().name(),
                    universeJpaEntity.getPublicStatus().name(),
                    universeJpaEntity.getUniverseHashtags().stream()
                            .map(universeHashtagJpaEntity -> universeHashtagJpaEntity.getHashtag().getTag())
                            .toList()
            );
        }
    }
}
