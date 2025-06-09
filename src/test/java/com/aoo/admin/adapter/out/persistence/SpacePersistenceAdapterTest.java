package com.aoo.admin.adapter.out.persistence;

import com.aoo.admin.adapter.out.persistence.mapper.SpaceMapper;
import com.aoo.admin.application.port.in.space.CreateSpaceResult;
import com.aoo.admin.domain.universe.space.Space;
import com.aoo.common.adapter.out.persistence.PersistenceAdapterTest;
import com.aoo.common.adapter.out.persistence.entity.SpaceJpaEntity;
import com.aoo.common.adapter.out.persistence.repository.SpaceJpaRepository;
import com.aoo.common.application.service.MockEntityFactoryService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@PersistenceAdapterTest
@Sql("SpacePersistenceAdapterTest.sql")
@Import({SpacePersistenceAdapter.class, SpaceMapper.class})
class SpacePersistenceAdapterTest {

    @Autowired
    SpacePersistenceAdapter sut;

    @Autowired
    SpaceJpaRepository spaceJpaRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("스페이스 저장하기")
    void testSaveSpace() {
        // given
        Space space = MockEntityFactoryService.getParentSpace();

        // when
        CreateSpaceResult result = sut.save(space);
        SpaceJpaEntity spaceJpaEntity = spaceJpaRepository.findById(result.spaceId()).orElseThrow();

        // then
        assertThat(spaceJpaEntity.getInnerImageFileId()).isEqualTo(space.getFileInfo().getInnerImageId());
        assertThat(spaceJpaEntity.getUniverseId()).isEqualTo(space.getBasicInfo().getUniverseId());
        assertThat(spaceJpaEntity.getSx()).isEqualTo(space.getPosInfo().getSx());
        assertThat(spaceJpaEntity.getSy()).isEqualTo(space.getPosInfo().getSy());
        assertThat(spaceJpaEntity.getEx()).isEqualTo(space.getPosInfo().getEx());
        assertThat(spaceJpaEntity.getEy()).isEqualTo(space.getPosInfo().getEy());
        assertThat(spaceJpaEntity.getTitle()).isEqualTo(space.getBasicInfo().getTitle());
        assertThat(spaceJpaEntity.getDescription()).isEqualTo(space.getBasicInfo().getDescription());
    }

    @Test
    @DisplayName("스페이스 조회 테스트")
    void testFindPiece() {
        // given
        Long pieceId = 1L;

        // when
        Space space = sut.find(pieceId);

        // then
        assertThat(space.getId()).isEqualTo(pieceId);
        assertThat(space.getFileInfo().getInnerImageId()).isEqualTo(1L);
        assertThat(space.getBasicInfo().getParentSpaceId()).isNull();
        assertThat(space.getBasicInfo().getUniverseId()).isNull();
        assertThat(space.getBasicInfo().getTitle()).isEqualTo("공간");
        assertThat(space.getBasicInfo().getDescription()).isEqualTo("스페이스는 공간입니다.");
        assertThat(space.getPosInfo().getSx()).isEqualTo(0.3f);
        assertThat(space.getPosInfo().getSy()).isEqualTo(0.2f);
        assertThat(space.getPosInfo().getEx()).isEqualTo(0.2f);
        assertThat(space.getPosInfo().getEy()).isEqualTo(0.3f);
        assertThat(space.getDateInfo().getCreatedTime()).isEqualTo(ZonedDateTime.of(2025, 6, 9, 10, 30, 0, 0, ZoneOffset.UTC));
        assertThat(space.getDateInfo().getUpdatedTime()).isEqualTo(ZonedDateTime.of(2025, 6, 9, 10, 30, 0, 0, ZoneOffset.UTC));
    }

    @Test
    @DisplayName("스페이스 수정 테스트")
    void testUpdatePiece() {
        // given
        Space space = Space.create(1L, 1L, 1L, -1L, "평화", "피스는 평화입니다.", 0.1f, 0.2f, 0.3f, 0.4f);

        // when
        sut.update(space);

        em.flush();
        em.clear();

        Space spaceInDB = sut.find(space.getId());

        // then
        assertThat(spaceInDB.getId()).isEqualTo(space.getId());
        assertThat(spaceInDB.getFileInfo().getInnerImageId()).isEqualTo(1L);
        assertThat(spaceInDB.getBasicInfo().getParentSpaceId()).isNull();
        assertThat(spaceInDB.getBasicInfo().getUniverseId()).isNull();
        assertThat(spaceInDB.getBasicInfo().getTitle()).isEqualTo("평화");
        assertThat(spaceInDB.getBasicInfo().getDescription()).isEqualTo("피스는 평화입니다.");
        assertThat(spaceInDB.getPosInfo().getSx()).isEqualTo(0.1f);
        assertThat(spaceInDB.getPosInfo().getSy()).isEqualTo(0.2f);
        assertThat(spaceInDB.getPosInfo().getEx()).isEqualTo(0.3f);
        assertThat(spaceInDB.getPosInfo().getEy()).isEqualTo(0.4f);
        assertThat(spaceInDB.getDateInfo().getCreatedTime()).isEqualTo(ZonedDateTime.of(2025, 6, 9, 10, 30, 0, 0, ZoneOffset.UTC));
        assertThat(spaceInDB.getDateInfo().getUpdatedTime()).isAfter(ZonedDateTime.of(2025, 6, 9, 10, 30, 0, 0, ZoneOffset.UTC));
    }
}