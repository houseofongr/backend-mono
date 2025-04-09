package com.hoo.aoo.admin.adapter.out.persistence.mapper;

import com.hoo.aoo.admin.domain.universe.space.Space;
import com.hoo.aoo.common.adapter.out.persistence.entity.SpaceJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class SpaceMapper {

    public Space mapToSingleEntity(SpaceJpaEntity spaceJpaEntity) {
        return Space.loadSingle(spaceJpaEntity.getId(),
                spaceJpaEntity.getImageFileId(),
                spaceJpaEntity.getUniverse().getId(),
                spaceJpaEntity.getTitle(),
                spaceJpaEntity.getDescription(),
                spaceJpaEntity.getCreatedTime(),
                spaceJpaEntity.getUpdatedTime(),
                spaceJpaEntity.getDx(),
                spaceJpaEntity.getDy(),
                spaceJpaEntity.getScaleX(),
                spaceJpaEntity.getScaleY(),
                spaceJpaEntity.getDepth());
    }

    public Space mapToAncestorEntity(SpaceJpaEntity spaceJpaEntity) {
        if (spaceJpaEntity.getParent() == null) {
            return mapToSingleEntity(spaceJpaEntity);
        }

        return Space.loadAncestor(spaceJpaEntity.getId(),
                spaceJpaEntity.getImageFileId(),
                spaceJpaEntity.getUniverse().getId(),
                spaceJpaEntity.getTitle(),
                spaceJpaEntity.getDescription(),
                spaceJpaEntity.getCreatedTime(),
                spaceJpaEntity.getUpdatedTime(),
                spaceJpaEntity.getDx(),
                spaceJpaEntity.getDy(),
                spaceJpaEntity.getScaleX(),
                spaceJpaEntity.getScaleY(),
                mapToAncestorEntity(spaceJpaEntity.getParent()));
    }
}
