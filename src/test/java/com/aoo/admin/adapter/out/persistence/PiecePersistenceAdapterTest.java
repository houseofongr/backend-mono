package com.aoo.admin.adapter.out.persistence;

import com.aoo.admin.domain.universe.space.element.Piece;
import com.aoo.common.adapter.out.persistence.PersistenceAdapterTest;
import com.aoo.common.adapter.out.persistence.entity.PieceJpaEntity;
import com.aoo.common.adapter.out.persistence.repository.PieceJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@PersistenceAdapterTest
@Import(PiecePersistenceAdapter.class)
class PiecePersistenceAdapterTest {

    @Autowired
    PiecePersistenceAdapter sut;

    @Autowired
    PieceJpaRepository pieceJpaRepository;

    @Test
    @DisplayName("피스 저장 테스트")
    void testSavePiece() {
        // given
        Piece newPiece = Piece.create(1L, -1L, 123L, 321L, "피스", "피스는 조각입니다.", 0.1f, 0.2f, 0.3f, 0.4f);

        // when
        Long newPieceId = sut.save(newPiece);
        PieceJpaEntity newPieceJpaEntity = pieceJpaRepository.findById(newPieceId).orElseThrow();

        // then
        assertThat(newPieceId).isEqualTo(newPiece.getId());
        assertThat(newPieceJpaEntity.getId()).isEqualTo(newPieceId);
        assertThat(newPieceJpaEntity.getUniverseId()).isEqualTo(123L);
        assertThat(newPieceJpaEntity.getParentSpaceId()).isEqualTo(321L);
        assertThat(newPieceJpaEntity.getTitle()).isEqualTo("피스");
        assertThat(newPieceJpaEntity.getDescription()).isEqualTo("피스는 조각입니다.");
        assertThat(newPieceJpaEntity.getSx()).isEqualTo(0.1f);
        assertThat(newPieceJpaEntity.getSy()).isEqualTo(0.2f);
        assertThat(newPieceJpaEntity.getEx()).isEqualTo(0.3f);
        assertThat(newPieceJpaEntity.getEy()).isEqualTo(0.4f);
    }

}