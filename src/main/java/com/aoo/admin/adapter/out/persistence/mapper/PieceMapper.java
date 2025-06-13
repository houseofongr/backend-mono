package com.aoo.admin.adapter.out.persistence.mapper;

import com.aoo.admin.application.port.in.piece.SearchPieceResult;
import com.aoo.admin.domain.universe.piece.Piece;
import com.aoo.common.adapter.out.persistence.entity.PieceJpaEntity;
import com.aoo.common.adapter.out.persistence.entity.SoundJpaEntity;
import com.aoo.common.application.port.in.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PieceMapper {

    public Piece mapToSingleDomainEntity(PieceJpaEntity pieceJpaEntity) {
        return Piece.loadWithoutRelation(pieceJpaEntity.getId(),
                pieceJpaEntity.getInnerImageFileId(),
                pieceJpaEntity.getTitle(),
                pieceJpaEntity.getDescription(),
                pieceJpaEntity.getCreatedTime(),
                pieceJpaEntity.getUpdatedTime(),
                pieceJpaEntity.getSx(),
                pieceJpaEntity.getSy(),
                pieceJpaEntity.getEx(),
                pieceJpaEntity.getEy()
        );
    }

    public SearchPieceResult mapToSearchPieceResult(PieceJpaEntity piece, Page<SoundJpaEntity> entityPage) {
        List<SoundJpaEntity> content = entityPage.getContent();
        return new SearchPieceResult(
                piece.getId(),
                piece.getTitle(),
                piece.getDescription(),
                piece.getCreatedTime().toEpochSecond(),
                piece.getUpdatedTime().toEpochSecond(),
                content.stream().map(soundJpaEntity -> new SearchPieceResult.SoundInfo(
                        soundJpaEntity.getId(),
                        soundJpaEntity.getAudioFileId(),
                        soundJpaEntity.getTitle(),
                        soundJpaEntity.getDescription(),
                        soundJpaEntity.getCreatedTime().toEpochSecond(),
                        soundJpaEntity.getUpdatedTime().toEpochSecond())).toList(),
                Pagination.of(entityPage)
        );
    }
}
