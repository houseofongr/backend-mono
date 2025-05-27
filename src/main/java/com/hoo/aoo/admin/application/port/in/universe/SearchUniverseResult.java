package com.hoo.aoo.admin.application.port.in.universe;

import com.hoo.aoo.common.adapter.out.persistence.entity.UniverseJpaEntity;
import com.hoo.aoo.common.application.port.in.Pagination;

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
}
