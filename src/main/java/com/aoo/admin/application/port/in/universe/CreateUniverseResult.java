package com.aoo.admin.application.port.in.universe;

import com.aoo.common.adapter.out.persistence.entity.UniverseJpaEntity;

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

    public static CreateUniverseResult of(UniverseJpaEntity universeJpaEntity) {
        return new CreateUniverseResult(
                String.format("[#%d]번 유니버스가 생성되었습니다.", universeJpaEntity.getId()),
                universeJpaEntity.getId(),
                universeJpaEntity.getThumbMusicFileId(),
                universeJpaEntity.getThumbnailFileId(),
                universeJpaEntity.getInnerImageFileId(),
                universeJpaEntity.getAuthor().getId(),
                universeJpaEntity.getCreatedTime().toEpochSecond(),
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
