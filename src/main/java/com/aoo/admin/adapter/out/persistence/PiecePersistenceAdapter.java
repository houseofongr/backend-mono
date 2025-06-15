package com.aoo.admin.adapter.out.persistence;

import com.aoo.admin.adapter.out.persistence.mapper.PieceMapper;
import com.aoo.admin.adapter.out.persistence.mapper.SoundMapper;
import com.aoo.admin.application.port.in.piece.SearchPieceCommand;
import com.aoo.admin.application.port.in.piece.SearchPieceResult;
import com.aoo.admin.application.port.out.piece.DeletePiecePort;
import com.aoo.admin.application.port.out.piece.FindPiecePort;
import com.aoo.admin.application.port.out.piece.SavePiecePort;
import com.aoo.admin.application.port.out.piece.UpdatePiecePort;
import com.aoo.admin.application.service.AdminErrorCode;
import com.aoo.admin.application.service.AdminException;
import com.aoo.admin.domain.universe.piece.Piece;
import com.aoo.common.adapter.out.persistence.entity.PieceJpaEntity;
import com.aoo.common.adapter.out.persistence.entity.SoundJpaEntity;
import com.aoo.common.adapter.out.persistence.repository.PieceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public Piece findWithSounds(Long id) {
        PieceJpaEntity pieceJpaEntity = pieceJpaRepository.findById(id).orElseThrow(() -> new AdminException(AdminErrorCode.PIECE_NOT_FOUND));
        return pieceMapper.mapToPieceWithSounds(pieceJpaEntity);
    }

    @Override
    public SearchPieceResult search(SearchPieceCommand command) {
        PieceJpaEntity pieceJpaEntity = pieceJpaRepository.findById(command.pieceId()).orElseThrow(() -> new AdminException(AdminErrorCode.PIECE_NOT_FOUND));
        return pieceMapper.mapToSearchPieceResult(pieceJpaEntity, pieceJpaRepository.searchAll(command));
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
