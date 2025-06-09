package com.aoo.admin.adapter.out.persistence.mapper;

import com.aoo.admin.domain.universe.space.Space;
import com.aoo.common.adapter.out.persistence.entity.SpaceJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class SpaceMapper {

    public Space mapToSingleDomainEntity(SpaceJpaEntity spaceJpaEntity) {
        return Space.loadSingle(spaceJpaEntity.getId(),
                spaceJpaEntity.getInnerImageFileId(),
                spaceJpaEntity.getTitle(),
                spaceJpaEntity.getDescription(),
                spaceJpaEntity.getCreatedTime(),
                spaceJpaEntity.getUpdatedTime(),
                spaceJpaEntity.getSx(),
                spaceJpaEntity.getSy(),
                spaceJpaEntity.getEx(),
                spaceJpaEntity.getEy());
    }

    public Space mapToAncestorEntity(SpaceJpaEntity spaceJpaEntity) {
        return null;
    }
//        if (spaceJpaEntity.getParent() == null) {
//            return mapToSingleEntity(spaceJpaEntity);
//        }
//
//        return Space.loadAncestor(spaceJpaEntity.getId(),
//                spaceJpaEntity.getInnerImageFileId(),
//                spaceJpaEntity.getUniverse().getId(),
//                spaceJpaEntity.getTitle(),
//                spaceJpaEntity.getDescription(),
//                spaceJpaEntity.getCreatedTime(),
//                spaceJpaEntity.getUpdatedTime(),
//                spaceJpaEntity.getDx(),
//                spaceJpaEntity.getDy(),
//                spaceJpaEntity.getScaleX(),
//                spaceJpaEntity.getScaleY(),
//                mapToAncestorEntity(spaceJpaEntity.getParent()));
//    }
}
