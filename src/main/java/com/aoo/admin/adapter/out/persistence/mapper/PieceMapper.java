package com.aoo.admin.adapter.out.persistence.mapper;

import com.aoo.admin.domain.universe.piece.Piece;
import com.aoo.common.adapter.out.persistence.entity.PieceJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class PieceMapper {

    public Piece mapToSingleDomainEntity(PieceJpaEntity pieceJpaEntity) {
        return Piece.loadSingle(pieceJpaEntity.getId(),
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
}
