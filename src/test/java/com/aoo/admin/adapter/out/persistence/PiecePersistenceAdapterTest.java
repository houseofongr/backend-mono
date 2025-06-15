package com.aoo.admin.adapter.out.persistence;

import com.aoo.admin.adapter.out.persistence.mapper.PieceMapper;
import com.aoo.admin.application.port.in.piece.SearchPieceCommand;
import com.aoo.admin.application.port.in.piece.SearchPieceResult;
import com.aoo.admin.domain.universe.piece.Piece;
import com.aoo.common.adapter.out.persistence.PersistenceAdapterTest;
import com.aoo.common.adapter.out.persistence.entity.PieceJpaEntity;
import com.aoo.common.adapter.out.persistence.repository.PieceJpaRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Sql("PiecePersistenceAdapterTest.sql")
@PersistenceAdapterTest
@Import({PiecePersistenceAdapter.class, PieceMapper.class})
class PiecePersistenceAdapterTest {

    @Autowired
    PiecePersistenceAdapter sut;

    @Autowired
    EntityManager em;

    @Autowired
    PieceJpaRepository pieceJpaRepository;

    @Test
    @DisplayName("피스 저장 테스트")
    void testSavePiece() {
        // given
        Piece newPiece = Piece.create(2L, -1L, 123L, 321L, "피스", "피스는 조각입니다.", 0.1f, 0.2f, 0.3f, 0.4f, false);

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

    @Test
    @DisplayName("피스 조회 테스트")
    void testFindPiece() {
        // given
        Long pieceId = 1L;

        // when
        Piece piece = sut.find(pieceId);

        // then
        assertThat(piece.getId()).isEqualTo(pieceId);
        assertThat(piece.getFileInfo().getImageId()).isNull();
        assertThat(piece.getBasicInfo().getParentSpaceId()).isNull();
        assertThat(piece.getBasicInfo().getUniverseId()).isNull();
        assertThat(piece.getBasicInfo().getTitle()).isEqualTo("조각");
        assertThat(piece.getBasicInfo().getDescription()).isEqualTo("피스는 조각입니다.");
        assertThat(piece.getPosInfo().getSx()).isEqualTo(0.3f);
        assertThat(piece.getPosInfo().getSy()).isEqualTo(0.2f);
        assertThat(piece.getPosInfo().getEx()).isEqualTo(0.2f);
        assertThat(piece.getPosInfo().getEy()).isEqualTo(0.3f);
        assertThat(piece.getDateInfo().getCreatedTime()).isEqualTo(ZonedDateTime.of(2025, 6, 9, 10, 30, 0, 0, ZoneOffset.UTC));
        assertThat(piece.getDateInfo().getUpdatedTime()).isEqualTo(ZonedDateTime.of(2025, 6, 9, 10, 30, 0, 0, ZoneOffset.UTC));
    }

    @Test
    @DisplayName("피스, 사운드 조회 테스트")
    void testFindWithSounds() {
        // given
        Long id = 1L;

        // when
        Piece pieceWithSounds = sut.findWithSounds(id);

        // then
        assertThat(pieceWithSounds.getId()).isEqualTo(1L);
        assertThat(pieceWithSounds.getFileInfo().getImageId()).isNull();
        assertThat(pieceWithSounds.getSounds()).hasSize(11);
    }

    @Test
    @DisplayName("피스 검색 테스트")
    void testSearchPiece() {
        // given
        SearchPieceCommand command = new SearchPieceCommand(Pageable.ofSize(10), 1L, "life", false);

        // when
        SearchPieceResult result = sut.search(command);

        // then
        assertThat(result.pieceId()).isEqualTo(1L);
        assertThat(result.title()).isEqualTo("조각");
        assertThat(result.description()).isEqualTo("피스는 조각입니다.");
        assertThat(result.createdTime()).isEqualTo(ZonedDateTime.of(2025, 6, 9, 10, 30, 0, 0, ZoneOffset.UTC).toEpochSecond());
        assertThat(result.updatedTime()).isEqualTo(ZonedDateTime.of(2025, 6, 9, 10, 30, 0, 0, ZoneOffset.UTC).toEpochSecond());
        assertThat(result.sounds()).hasSize(10).noneMatch(soundInfo -> soundInfo.soundId().equals(1L));
    }

    @Test
    @DisplayName("피스 수정 테스트")
    void testUpdatePiece() {
        // given
        Piece updatedPiece = Piece.create(1L, -1L, 123L, 321L, "평화", "피스는 평화입니다.", 0.1f, 0.2f, 0.3f, 0.4f, false);
        Long pieceId = 1L;

        // when
        sut.update(pieceId, updatedPiece);

        em.flush();
        em.clear();
        Piece piece = sut.find(pieceId);

        // then
        assertThat(piece.getId()).isEqualTo(pieceId);
        assertThat(piece.getFileInfo().getImageId()).isNull();
        assertThat(piece.getBasicInfo().getParentSpaceId()).isNull();
        assertThat(piece.getBasicInfo().getUniverseId()).isNull();
        assertThat(piece.getBasicInfo().getTitle()).isEqualTo("평화");
        assertThat(piece.getBasicInfo().getDescription()).isEqualTo("피스는 평화입니다.");
        assertThat(piece.getPosInfo().getSx()).isEqualTo(0.1f);
        assertThat(piece.getPosInfo().getSy()).isEqualTo(0.2f);
        assertThat(piece.getPosInfo().getEx()).isEqualTo(0.3f);
        assertThat(piece.getPosInfo().getEy()).isEqualTo(0.4f);
        assertThat(piece.getDateInfo().getCreatedTime()).isEqualTo(ZonedDateTime.of(2025, 6, 9, 10, 30, 0, 0, ZoneOffset.UTC));
        assertThat(piece.getDateInfo().getUpdatedTime()).isAfter(ZonedDateTime.of(2025, 6, 9, 10, 30, 0, 0, ZoneOffset.UTC));
    }

    @Test
    @DisplayName("피스 삭제 테스트")
    void testDeletePiece() {
        // given
        List<Long> ids = List.of(1L);

        // when
        sut.deleteAll(ids);

        // then
        assertThat(pieceJpaRepository.findById(ids.getFirst())).isEmpty();
    }

}