package com.aoo.aar.adapter.out.persistence.mapper;

import com.aoo.aar.application.port.in.universe.SearchPublicUniverseResult;
import com.aoo.common.adapter.out.persistence.entity.UniverseJpaEntity;
import org.springframework.stereotype.Component;

@Component("AARUniverseMapper")
public class UniverseMapper {
    public SearchPublicUniverseResult.UniverseListInfo mapToSearchPublicUniverseListInfo(Long userId, UniverseJpaEntity universeJpaEntity) {
        return new SearchPublicUniverseResult.UniverseListInfo(
                universeJpaEntity.getId(),
                universeJpaEntity.getThumbnailFileId(),
                universeJpaEntity.getThumbMusicFileId(),
                universeJpaEntity.getAuthor().getId(),
                universeJpaEntity.getCreatedTime().toEpochSecond(),
                universeJpaEntity.getViewCount(),
                universeJpaEntity.getUniverseLikes().size(),
                userId == null ?
                        null :
                        universeJpaEntity.getUniverseLikes().stream()
                                .anyMatch(universeLikeJpaEntity -> universeLikeJpaEntity.getUser().getId().equals(userId)),
                universeJpaEntity.getTitle(),
                universeJpaEntity.getDescription(),
                universeJpaEntity.getAuthor().getNickname(),
                universeJpaEntity.getUniverseHashtags().stream()
                        .map(universeHashtagJpaEntity -> universeHashtagJpaEntity.getHashtag().getTag())
                        .toList()
        );
    }
}
