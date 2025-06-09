package com.aoo.admin.adapter.out.persistence;

import com.aoo.admin.application.port.out.piece.SavePiecePort;
import com.aoo.admin.domain.universe.space.element.Piece;
import com.aoo.common.adapter.out.persistence.entity.PieceJpaEntity;
import com.aoo.common.adapter.out.persistence.repository.PieceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PiecePersistenceAdapter implements SavePiecePort {

    private final PieceJpaRepository pieceJpaRepository;

    @Override
    public Long save(Piece piece) {
        PieceJpaEntity pieceJpaEntity = PieceJpaEntity.create(piece);
        pieceJpaRepository.save(pieceJpaEntity);

        return pieceJpaEntity.getId();
    }
}
