package com.aoo.admin.adapter.out.persistence;

import com.aoo.admin.adapter.out.persistence.mapper.SpaceMapper;
import com.aoo.admin.application.port.in.space.CreateSpaceResult;
import com.aoo.admin.domain.universe.space.Space;
import com.aoo.common.adapter.out.persistence.PersistenceAdapterTest;
import com.aoo.common.adapter.out.persistence.entity.SpaceJpaEntity;
import com.aoo.common.adapter.out.persistence.repository.SpaceJpaRepository;
import com.aoo.common.application.service.MockEntityFactoryService;
import jakarta.persistence.EntityManager;
import org.assertj.core.data.TemporalUnitWithinOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;


@PersistenceAdapterTest
@Import({SpacePersistenceAdapter.class, SpaceMapper.class})
class SpacePersistenceAdapterTest {

    @Autowired
    SpacePersistenceAdapter sut;

    @Autowired
    SpaceJpaRepository spaceJpaRepository;

    @Autowired
    EntityManager em;

    @Test
    @Sql("SpacePersistenceAdapterTest2.sql")
    @DisplayName("스페이스 저장하기")
    void testSaveSpace() {
        // given
        Space space = MockEntityFactoryService.getParentSpace();

        // when
        CreateSpaceResult result = sut.save(1L, space);
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

//    @Test
//    @Sql("SpacePersistenceAdapterTest.sql")
//    @DisplayName("정상 수정로직(Happy case) + 수정 완료 시 수정일자(UpdatedTime) 변경")
//    void testUpdateSpace() {
//        // given
//        Space space = Space.loadSingle(1L, 2L, 1L, "블랙홀", "블랙홀은 빛도 빨아들입니다.", null, null, 0.1f, 0.2f, 0.3f, 0.4f, 1);
//
//        // when
//        sut.update(space);
//        SpaceJpaEntity spaceJpaEntity = spaceJpaRepository.findById(1L).orElseThrow();
//        em.flush();
//        em.clear();
//
//        // then
//        assertThat(spaceJpaEntity.getInnerImageFileId()).isEqualTo(space.getInnerImageId());
//        assertThat(spaceJpaEntity.getTitle()).isEqualTo(space.getBasicInfo().getTitle());
//        assertThat(spaceJpaEntity.getDescription()).isEqualTo(space.getBasicInfo().getDescription());
//        assertThat(spaceJpaEntity.getDx()).isEqualTo(space.getPosInfo().getDx());
//        assertThat(spaceJpaEntity.getDy()).isEqualTo(space.getPosInfo().getDy());
//        assertThat(spaceJpaEntity.getScaleX()).isEqualTo(space.getPosInfo().getScaleX());
//        assertThat(spaceJpaEntity.getScaleY()).isEqualTo(space.getPosInfo().getScaleY());
//        assertThat(spaceJpaEntity.getUpdatedTime()).isCloseTo(ZonedDateTime.now(), new TemporalUnitWithinOffset(1L, ChronoUnit.SECONDS));
//    }
}