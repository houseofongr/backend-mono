package com.hoo.aoo.admin.adapter.out.persistence.mapper;

import com.hoo.aoo.admin.domain.universe.Category;
import com.hoo.aoo.admin.domain.universe.PublicStatus;
import com.hoo.aoo.admin.domain.universe.Universe;
import com.hoo.aoo.common.adapter.out.persistence.entity.*;
import org.assertj.core.data.TemporalUnitWithinOffset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class UniverseMapperTest {

    UniverseMapper sut;

    @BeforeEach
    void init() {
        sut = new UniverseMapper();
    }

    @Test
    @DisplayName("유니버스 불러오기")
    void testLoadUniverse() {
        // given
        UniverseJpaEntity universeJpaEntity = new UniverseJpaEntity(1L, "우주", "이곳은 우주입니다.", 12L, PublicStatus.PUBLIC, Category.LIFE, 13L, 102L, null, new ArrayList<>(), new ArrayList<>());
        universeJpaEntity.getUniverseHashtags().add(new UniverseHashtagJpaEntity(1L, universeJpaEntity, new HashtagJpaEntity(1L,"우주")));
        universeJpaEntity.getUniverseHashtags().add(new UniverseHashtagJpaEntity(2L, universeJpaEntity, new HashtagJpaEntity(2L,"행성")));
        universeJpaEntity.getUniverseHashtags().add(new UniverseHashtagJpaEntity(3L, universeJpaEntity, new HashtagJpaEntity(3L,"지구")));
        universeJpaEntity.getUniverseHashtags().add(new UniverseHashtagJpaEntity(4L, universeJpaEntity, new HashtagJpaEntity(4L,"별")));
        universeJpaEntity.getUniverseLikes().add(new UniverseLikeJpaEntity(1L, universeJpaEntity, new UserJpaEntity()));
        universeJpaEntity.getUniverseLikes().add(new UniverseLikeJpaEntity(2L, universeJpaEntity, new UserJpaEntity()));
        universeJpaEntity.prePersist();

        // when
        Universe universe = sut.mapToDomainEntity(universeJpaEntity);

        // then
        assertThat(universe.getId()).isEqualTo(universeJpaEntity.getId());
        assertThat(universe.getThumbnailId()).isEqualTo(universeJpaEntity.getThumbnailFileId());
        assertThat(universe.getThumbMusicId()).isEqualTo(universeJpaEntity.getThumbMusicFileId());
        assertThat(universe.getDateInfo().getCreatedTime()).isCloseTo(ZonedDateTime.now(), new TemporalUnitWithinOffset(100L,ChronoUnit.MILLIS));
        assertThat(universe.getDateInfo().getUpdatedTime()).isCloseTo(ZonedDateTime.now(), new TemporalUnitWithinOffset(100L,ChronoUnit.MILLIS));
        assertThat(universe.getBasicInfo().getPublicStatus()).isEqualTo(universeJpaEntity.getPublicStatus());
        assertThat(universe.getBasicInfo().getTitle()).isEqualTo(universeJpaEntity.getTitle());
        assertThat(universe.getBasicInfo().getDescription()).isEqualTo(universeJpaEntity.getDescription());
        assertThat(universe.getBasicInfo().getCategory()).isEqualTo(universeJpaEntity.getCategory());
        assertThat(universe.getSocialInfo().getHashtags()).hasSize(4);
        assertThat(universe.getSocialInfo().getHashtags()).contains("우주", "행성", "지구", "별");
        assertThat(universe.getSocialInfo().getLikeCount()).isEqualTo(2L);
        assertThat(universe.getSocialInfo().getViewCount()).isEqualTo(12L);
    }
}