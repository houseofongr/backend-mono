package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.admin.domain.universe.Universe;
import com.hoo.aoo.common.adapter.out.persistence.PersistenceAdapterTest;
import com.hoo.aoo.common.adapter.out.persistence.entity.HashtagJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.entity.UniverseJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.repository.UniverseJpaRepository;
import com.hoo.aoo.common.application.service.MockEntityFactoryService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@PersistenceAdapterTest
@Import(UniversePersistenceAdapter.class)
class UniversePersistenceAdapterTest {

    @Autowired
    UniversePersistenceAdapter sut;

    @Autowired
    UniverseJpaRepository universeJpaRepository;

    @Autowired
    EntityManager em;

    @Test
    @Sql("UniversePersistenceAdapter.sql")
    @DisplayName("해시태그 존재여부 확인 후 생성 테스트")
    void testGetOrCreateHashtag() {
        // given
        String existHashtag = "exist";
        String notExistHashtag = "not_exist";

        // when
        HashtagJpaEntity entity = sut.getOrCreate(existHashtag);
        HashtagJpaEntity entity2 = sut.getOrCreate(notExistHashtag);

        // then
        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity2.getId()).isNotEqualTo(1L);
    }

    @Test
    @DisplayName("유니버스 저장 테스트")
    void testSaveUniverse() {
        // given
        Universe universe = MockEntityFactoryService.getUniverse();

        // when
        Long id = sut.save(universe);
        UniverseJpaEntity universeJpaEntity = universeJpaRepository.findById(id).orElseThrow();

        // then
        assertThat(universeJpaEntity.getId()).isEqualTo(id);
        assertThat(universeJpaEntity.getTitle()).isEqualTo(universe.getBasicInfo().getTitle());
        assertThat(universeJpaEntity.getDescription()).isEqualTo(universe.getBasicInfo().getDescription());
        assertThat(universeJpaEntity.getPublicStatus()).isEqualTo(universe.getBasicInfo().getPublicStatus());
        assertThat(universeJpaEntity.getCategory()).isEqualTo(universe.getBasicInfo().getCategory());
        assertThat(universeJpaEntity.getViewCount()).isEqualTo(0L);
        assertThat(universeJpaEntity.getUniverseHashtags()).hasSize(4);
        assertThat(universeJpaEntity.getUniverseLikes()).isEmpty();
        assertThat(universeJpaEntity.getThumbnailFileId()).isEqualTo(universe.getThumbnailId());
        assertThat(universeJpaEntity.getThumbMusicFileId()).isEqualTo(universe.getThumbMusicId());
    }
}