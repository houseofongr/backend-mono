package com.hoo.aoo.admin.adapter.out.persistence.mapper;

import com.hoo.aoo.admin.adapter.out.persistence.entity.*;
import com.hoo.aoo.admin.domain.item.*;
import com.hoo.aoo.admin.domain.item.soundsource.SoundSource;
import com.hoo.aoo.common.FixtureRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ItemMapperTest {

    ItemMapper sut = new ItemMapper();

    @Test
    @DisplayName("음원 엔티티 도메인 => JPA 변환 테스트")
    void testMapToNewSoundSourceJpaEntity() {
        // given
        SoundSource soundSource = FixtureRepository.getSoundSource();

        // when
        SoundSourceJpaEntity soundSourceJpaEntity = sut.mapToNewJpaEntity(soundSource);

        // then
        assertThat(soundSourceJpaEntity).isNotNull();
        assertThat(soundSourceJpaEntity.getId()).isEqualTo(null);
        assertThat(soundSourceJpaEntity.getName()).isEqualTo(soundSource.getDetail().getName());
        assertThat(soundSourceJpaEntity.getDescription()).isEqualTo(soundSource.getDetail().getDescription());
        assertThat(soundSourceJpaEntity.getAudioFileId()).isEqualTo(soundSource.getFile().getFileId().getId());
        assertThat(soundSourceJpaEntity.getItem()).isEqualTo(null);
        assertThat(soundSourceJpaEntity.getIsActive()).isEqualTo(true);
    }

    @Test
    @DisplayName("아이템 엔티티 도메인 => JPA 변환 테스트(직사각형)")
    void testMapToNewJpaEntity_Rectangle() {
        // given
        Item 설이 = FixtureRepository.getItem("설이", ItemType.RECTANGLE);

        // when
        ItemJpaEntity itemJpaEntity = sut.mapToNewJpaEntity(설이, null, null, null);

        // then
        assertThat(itemJpaEntity).isNotNull();
        assertThat(itemJpaEntity.getId()).isNull();
        assertThat(itemJpaEntity.getName()).isEqualTo(설이.getItemId().getName());
        assertThat(((RectangleItemJpaEntity) itemJpaEntity).getX()).isEqualTo(설이.getShape().getX());
        assertThat(((RectangleItemJpaEntity) itemJpaEntity).getY()).isEqualTo(설이.getShape().getX());
        assertThat(((RectangleItemJpaEntity) itemJpaEntity).getWidth()).isEqualTo(((Rectangle)설이.getShape()).getWidth());
        assertThat(((RectangleItemJpaEntity) itemJpaEntity).getHeight()).isEqualTo(((Rectangle)설이.getShape()).getHeight());
        assertThat(((RectangleItemJpaEntity) itemJpaEntity).getAngle()).isEqualTo(((Rectangle) 설이.getShape()).getAngle());
        assertThat(itemJpaEntity.getSoundSources().getFirst().getDescription()).isEqualTo(sut.mapToNewJpaEntity(설이.getSoundSources().getFirst()).getDescription());
    }

    @Test
    @DisplayName("아이템 엔티티 도메인 => JPA 변환 테스트(원형)")
    void testMapToNewJpaEntity_Circle() {
        // given
        Item 강아지 = FixtureRepository.getItem("강아지", ItemType.CIRCLE);

        // when
        ItemJpaEntity itemJpaEntity = sut.mapToNewJpaEntity(강아지, null, null, null);

        // then
        assertThat(itemJpaEntity).isNotNull();
        assertThat(itemJpaEntity.getId()).isNull();
        assertThat(itemJpaEntity.getName()).isEqualTo(강아지.getItemId().getName());
        assertThat(((CircleItemJpaEntity) itemJpaEntity).getX()).isEqualTo(강아지.getShape().getX());
        assertThat(((CircleItemJpaEntity) itemJpaEntity).getY()).isEqualTo(강아지.getShape().getX());
        assertThat(((CircleItemJpaEntity) itemJpaEntity).getRadius()).isEqualTo(((Circle)강아지.getShape()).getRadius());
        assertThat(itemJpaEntity.getSoundSources().getFirst().getDescription()).isEqualTo(sut.mapToNewJpaEntity(강아지.getSoundSources().getFirst()).getDescription());
    }

    @Test
    @DisplayName("아이템 엔티티 도메인 => JPA 변환 테스트(타원형)")
    void testMapToNewJpaEntity_Ellipse() {
        // given
        Item 화분 = FixtureRepository.getItem("화분", ItemType.ELLIPSE);

        // when
        ItemJpaEntity itemJpaEntity = sut.mapToNewJpaEntity(화분, null, null, null);

        // then
        assertThat(itemJpaEntity).isNotNull();
        assertThat(itemJpaEntity.getId()).isNull();
        assertThat(itemJpaEntity.getName()).isEqualTo(화분.getItemId().getName());
        assertThat(((EllipseItemJpaEntity) itemJpaEntity).getX()).isEqualTo(화분.getShape().getX());
        assertThat(((EllipseItemJpaEntity) itemJpaEntity).getY()).isEqualTo(화분.getShape().getX());
        assertThat(((EllipseItemJpaEntity) itemJpaEntity).getWidth()).isEqualTo(((Ellipse)화분.getShape()).getWidth());
        assertThat(((EllipseItemJpaEntity) itemJpaEntity).getHeight()).isEqualTo(((Ellipse)화분.getShape()).getHeight());
        assertThat(((EllipseItemJpaEntity) itemJpaEntity).getAngle()).isEqualTo(((Ellipse) 화분.getShape()).getAngle());
        assertThat(itemJpaEntity.getSoundSources().getFirst().getDescription()).isEqualTo(sut.mapToNewJpaEntity(화분.getSoundSources().getFirst()).getDescription());
    }

}