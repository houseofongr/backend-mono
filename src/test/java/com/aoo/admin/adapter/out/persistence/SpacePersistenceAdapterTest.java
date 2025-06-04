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
    @Sql("SpacePersistenceAdapterTest.sql")
    @DisplayName("스페이스 단건 불러오기")
    void loadSingleSpace() {
        // given
        Long id = 1L;

        // when
        Space load = sut.loadSingle(id).orElseThrow();

        // then
        assertThat(load.getId()).isEqualTo(id);
        assertThat(load.isRoot()).isTrue();
        assertThat(load.getTreeInfo().getParentSpace()).isNull();
        assertThat(load.getTreeInfo().getChildSpaces()).isEmpty();
    }

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
        assertThat(spaceJpaEntity.getInnerImageFileId()).isEqualTo(space.getInnerImageId());
        assertThat(spaceJpaEntity.getUniverse().getId()).isEqualTo(space.getUniverseId());
        assertThat(spaceJpaEntity.getDx()).isEqualTo(space.getPosInfo().getDx());
        assertThat(spaceJpaEntity.getDy()).isEqualTo(space.getPosInfo().getDy());
        assertThat(spaceJpaEntity.getScaleX()).isEqualTo(space.getPosInfo().getScaleX());
        assertThat(spaceJpaEntity.getScaleY()).isEqualTo(space.getPosInfo().getScaleY());
        assertThat(spaceJpaEntity.getTitle()).isEqualTo(space.getBasicInfo().getTitle());
        assertThat(spaceJpaEntity.getDescription()).isEqualTo(space.getBasicInfo().getDescription());
        assertThat(spaceJpaEntity.getDepth()).isEqualTo(space.getTreeInfo().getDepth());
    }

    @Test
    @Sql("SpacePersistenceAdapterTest2.sql")
    @DisplayName("자식 스페이스 저장하기")
    void testSaveChildSpace() {
        // given
        Space parent = MockEntityFactoryService.getParentSpace();
        CreateSpaceResult parentSpaceResult = sut.save(1L, parent);
        em.flush();
        em.clear();

        Space child = MockEntityFactoryService.getChildSpace(parentSpaceResult.spaceId());

        // when
        CreateSpaceResult childSpaceResult = sut.save(1L, child);
        SpaceJpaEntity spaceJpaEntity = spaceJpaRepository.findById(childSpaceResult.spaceId()).orElseThrow();

        // then
        assertThat(spaceJpaEntity.getId()).isEqualTo(childSpaceResult.spaceId());
        assertThat(spaceJpaEntity.getInnerImageFileId()).isEqualTo(child.getInnerImageId());
        assertThat(spaceJpaEntity.getUniverse().getId()).isEqualTo(child.getUniverseId());
        assertThat(spaceJpaEntity.getDx()).isEqualTo(child.getPosInfo().getDx());
        assertThat(spaceJpaEntity.getDy()).isEqualTo(child.getPosInfo().getDy());
        assertThat(spaceJpaEntity.getScaleX()).isEqualTo(child.getPosInfo().getScaleX());
        assertThat(spaceJpaEntity.getScaleY()).isEqualTo(child.getPosInfo().getScaleY());
        assertThat(spaceJpaEntity.getTitle()).isEqualTo(child.getBasicInfo().getTitle());
        assertThat(spaceJpaEntity.getDescription()).isEqualTo(child.getBasicInfo().getDescription());
        assertThat(spaceJpaEntity.getParent().getId()).isEqualTo(parentSpaceResult.spaceId());
        assertThat(spaceJpaEntity.getDepth()).isEqualTo(child.getTreeInfo().getDepth());
    }

    @Test
    @Sql("SpacePersistenceAdapterTest.sql")
    @DisplayName("정상 수정로직(Happy case) + 수정 완료 시 수정일자(UpdatedTime) 변경")
    void testUpdateSpace() {
        // given
        Space space = Space.loadSingle(1L, 2L, 1L, "블랙홀", "블랙홀은 빛도 빨아들입니다.", null, null, 0.1f, 0.2f, 0.3f, 0.4f, 1);

        // when
        sut.update(space);
        SpaceJpaEntity spaceJpaEntity = spaceJpaRepository.findById(1L).orElseThrow();
        em.flush();
        em.clear();

        // then
        assertThat(spaceJpaEntity.getInnerImageFileId()).isEqualTo(space.getInnerImageId());
        assertThat(spaceJpaEntity.getTitle()).isEqualTo(space.getBasicInfo().getTitle());
        assertThat(spaceJpaEntity.getDescription()).isEqualTo(space.getBasicInfo().getDescription());
        assertThat(spaceJpaEntity.getDx()).isEqualTo(space.getPosInfo().getDx());
        assertThat(spaceJpaEntity.getDy()).isEqualTo(space.getPosInfo().getDy());
        assertThat(spaceJpaEntity.getScaleX()).isEqualTo(space.getPosInfo().getScaleX());
        assertThat(spaceJpaEntity.getScaleY()).isEqualTo(space.getPosInfo().getScaleY());
        assertThat(spaceJpaEntity.getUpdatedTime()).isCloseTo(ZonedDateTime.now(), new TemporalUnitWithinOffset(1L, ChronoUnit.SECONDS));
    }
}