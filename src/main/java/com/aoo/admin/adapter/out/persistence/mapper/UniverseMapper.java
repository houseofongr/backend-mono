package com.aoo.admin.adapter.out.persistence.mapper;

import com.aoo.admin.application.port.in.universe.CreateUniverseResult;
import com.aoo.admin.application.port.in.universe.SearchUniverseResult;
import com.aoo.admin.domain.universe.TraversalComponents;
import com.aoo.admin.domain.universe.Universe;
import com.aoo.admin.domain.universe.space.Space;
import com.aoo.admin.domain.universe.piece.Piece;
import com.aoo.common.adapter.out.persistence.entity.PieceJpaEntity;
import com.aoo.common.adapter.out.persistence.entity.SpaceJpaEntity;
import com.aoo.common.adapter.out.persistence.entity.UniverseJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UniverseMapper {

    public CreateUniverseResult mapToCreateUniverseResult(UniverseJpaEntity universeJpaEntity) {
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

    public Universe mapToDomainEntity(UniverseJpaEntity universeJpaEntity) {
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

    public TraversalComponents mapToTraversalComponent(UniverseJpaEntity universeJpaEntity, List<SpaceJpaEntity> spaceJpaEntities, List<PieceJpaEntity> pieceJpaEntities) {
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
                spaceJpaEntity.getHidden(),
                spaceJpaEntity.getCreatedTime(),
                spaceJpaEntity.getUpdatedTime()
        )).toList();

        List<Piece> pieces = pieceJpaEntities.stream().map(pieceJpaEntity -> Piece.loadTreeComponent(
                pieceJpaEntity.getId(),
                pieceJpaEntity.getInnerImageFileId(),
                pieceJpaEntity.getUniverseId(),
                pieceJpaEntity.getParentSpaceId(),
                pieceJpaEntity.getTitle(),
                pieceJpaEntity.getDescription(),
                pieceJpaEntity.getSx(),
                pieceJpaEntity.getSy(),
                pieceJpaEntity.getEx(),
                pieceJpaEntity.getEy(),
                pieceJpaEntity.getHidden(),
                pieceJpaEntity.getCreatedTime(),
                pieceJpaEntity.getUpdatedTime()
        )).toList();

        return new TraversalComponents(universe, spaces, pieces);
    }


    public SearchUniverseResult.UniverseListInfo mapToSearchUniverseListInfo(UniverseJpaEntity universeJpaEntity) {
        return new SearchUniverseResult.UniverseListInfo(
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

    public SearchUniverseResult.UniverseDetailInfo mapToSearchUniverseDetailInfo(UniverseJpaEntity universeJpaEntity) {
        return new SearchUniverseResult.UniverseDetailInfo(
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
