package com.aoo.admin.adapter.out.persistence.mapper;

import com.aoo.admin.domain.universe.TraversalComponents;
import com.aoo.admin.domain.universe.Universe;
import com.aoo.admin.domain.universe.space.Space;
import com.aoo.admin.domain.universe.space.piece.Piece;
import com.aoo.common.adapter.out.persistence.entity.PieceJpaEntity;
import com.aoo.common.adapter.out.persistence.entity.SpaceJpaEntity;
import com.aoo.common.adapter.out.persistence.entity.UniverseJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UniverseMapper {
    public Universe mapToDomainEntity(UniverseJpaEntity universeJpaEntity){
        return Universe.load(universeJpaEntity.getId(),
                universeJpaEntity.getThumbMusicFileId(),
                universeJpaEntity.getThumbnailFileId(),
                universeJpaEntity.getInnerImageFileId(),
                universeJpaEntity.getAuthor().getId(),
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

    public TraversalComponents mapToTraversalComponent(UniverseJpaEntity universeJpaEntity, List<SpaceJpaEntity> spaceJpaEntities, List<PieceJpaEntity> elementJpaEntities) {
        Universe universe = Universe.loadTreeComponent(
                universeJpaEntity.getId(),
                universeJpaEntity.getInnerImageFileId()
        );

        List<Space> spaces = spaceJpaEntities.stream().map(spaceJpaEntity -> Space.loadTreeComponent(
                spaceJpaEntity.getId(),
                spaceJpaEntity.getInnerImageFileId(),
                spaceJpaEntity.getUniverseId(),
                spaceJpaEntity.getParentSpaceId(),
                spaceJpaEntity.getTitle(),
                spaceJpaEntity.getDescription(),
                spaceJpaEntity.getSx(),
                spaceJpaEntity.getSy(),
                spaceJpaEntity.getEx(),
                spaceJpaEntity.getEy(),
                spaceJpaEntity.getCreatedTime(),
                spaceJpaEntity.getUpdatedTime()
        )).toList();

        List<Piece> pieces = elementJpaEntities.stream().map(elementJpaEntity -> Piece.loadTreeComponent(
                elementJpaEntity.getId(),
                elementJpaEntity.getInnerImageFileId(),
                elementJpaEntity.getUniverseId(),
                elementJpaEntity.getParentSpaceId(),
                elementJpaEntity.getTitle(),
                elementJpaEntity.getDescription(),
                elementJpaEntity.getSx(),
                elementJpaEntity.getSy(),
                elementJpaEntity.getEx(),
                elementJpaEntity.getEy(),
                elementJpaEntity.getCreatedTime(),
                elementJpaEntity.getUpdatedTime()
        )).toList();

        return new TraversalComponents(universe, spaces, pieces);
    }
}
