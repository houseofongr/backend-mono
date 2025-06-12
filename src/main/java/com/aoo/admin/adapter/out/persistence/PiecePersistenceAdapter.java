package com.aoo.admin.adapter.out.persistence;

import com.aoo.admin.adapter.out.persistence.mapper.PieceMapper;
import com.aoo.admin.application.port.out.piece.DeletePiecePort;
import com.aoo.admin.application.port.out.piece.FindPiecePort;
import com.aoo.admin.application.port.out.piece.SavePiecePort;
import com.aoo.admin.application.port.out.piece.UpdatePiecePort;
import com.aoo.admin.application.service.AdminErrorCode;
import com.aoo.admin.application.service.AdminException;
import com.aoo.admin.domain.universe.piece.Piece;
import com.aoo.common.adapter.out.persistence.entity.PieceJpaEntity;
import com.aoo.common.adapter.out.persistence.repository.PieceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PiecePersistenceAdapter implements SavePiecePort, FindPiecePort, UpdatePiecePort, DeletePiecePort {

    private final PieceJpaRepository pieceJpaRepository;
    private final PieceMapper pieceMapper;

    @Override
    public Long save(Piece piece) {
        PieceJpaEntity pieceJpaEntity = PieceJpaEntity.create(piece);
        pieceJpaRepository.save(pieceJpaEntity);

        return pieceJpaEntity.getId();
    }

    @Override
    public Piece find(Long id) {
        PieceJpaEntity pieceJpaEntity = pieceJpaRepository.findById(id).orElseThrow(() -> new AdminException(AdminErrorCode.PIECE_NOT_FOUND));
        return pieceMapper.mapToSingleDomainEntity(pieceJpaEntity);
    }

    @Override
    public void update(Long pieceId, Piece piece) {
        PieceJpaEntity pieceJpaEntity = pieceJpaRepository.findById(pieceId).orElseThrow(() -> new AdminException(AdminErrorCode.PIECE_NOT_FOUND));
        pieceJpaEntity.update(piece);
    }

    @Override
    public void delete(Long id) {
        pieceJpaRepository.deleteById(id);
    }

    @Override
    public void deleteAll(List<Long> ids) {
        pieceJpaRepository.deleteAllById(ids);
    }
}
