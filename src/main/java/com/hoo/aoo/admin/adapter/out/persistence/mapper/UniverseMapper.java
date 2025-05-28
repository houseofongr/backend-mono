package com.hoo.aoo.admin.adapter.out.persistence.mapper;

import com.hoo.aoo.admin.domain.universe.Universe;
import com.hoo.aoo.common.adapter.out.persistence.entity.UniverseJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class UniverseMapper {
    public Universe mapToDomainEntity(UniverseJpaEntity universeJpaEntity){
        return Universe.load(universeJpaEntity.getId(),
                universeJpaEntity.getThumbnailFileId(),
                universeJpaEntity.getThumbMusicFileId(),
                universeJpaEntity.getInnerImageId(),
                universeJpaEntity.getTitle(),
                universeJpaEntity.getDescription(),
                universeJpaEntity.getCategory(),
                universeJpaEntity.getPublicStatus(),
                universeJpaEntity.getUniverseLikes().size(),
                universeJpaEntity.getViewCount(),
                universeJpaEntity.getUniverseHashtags().stream()
                        .map(universeHashtagJpaEntity -> universeHashtagJpaEntity.getHashtag().getTag()).toList(),
                universeJpaEntity.getCreatedTime(),
                universeJpaEntity.getUpdatedTime());
    }
}
