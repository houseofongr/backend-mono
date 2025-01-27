package com.hoo.aoo.admin.adapter.out.persistence.mapper;

import com.hoo.aoo.admin.adapter.out.persistence.entity.*;
import com.hoo.aoo.admin.domain.item.Circle;
import com.hoo.aoo.admin.domain.item.Ellipse;
import com.hoo.aoo.admin.domain.item.Item;
import com.hoo.aoo.admin.domain.item.Rectangle;
import com.hoo.aoo.admin.domain.soundsource.SoundSource;
import com.hoo.aoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ItemMapperTest {

    ItemMapper sut = new ItemMapper(new SoundSourceMapper());

    @Test
    @DisplayName("음원 엔티티 도메인 => JPA 변환 테스트")
    void testMapToNewSoundSourceJpaEntity() {
        // given
        SoundSource soundSource = MockEntityFactoryService.getSoundSource();

        // when
        SoundSourceJpaEntity soundSourceJpaEntity = sut.mapToNewJpaEntity(soundSource);

        // then
        assertThat(soundSourceJpaEntity).isNotNull();
        assertThat(soundSourceJpaEntity.getId()).isEqualTo(null);
        assertThat(soundSourceJpaEntity.getName()).isEqualTo(soundSource.getSoundSourceDetail().getName());
        assertThat(soundSourceJpaEntity.getDescription()).isEqualTo(soundSource.getSoundSourceDetail().getDescription());
        assertThat(soundSourceJpaEntity.getAudioFileId()).isEqualTo(soundSource.getFile().getFileId().getId());
        assertThat(soundSourceJpaEntity.getItem()).isEqualTo(null);
        assertThat(soundSourceJpaEntity.getIsActive()).isEqualTo(true);
    }

    @Test
    @DisplayName("아이템 엔티티 도메인 => JPA 변환 테스트(직사각형)")
    void testMapToNewJpaEntity_Rectangle() throws Exception {
        // given
        Item 설이 = MockEntityFactoryService.getRectangleItem();

        // when
        ItemJpaEntity itemJpaEntity = sut.mapToNewJpaEntity(설이, null, null, null);

        // then
        assertThat(itemJpaEntity).isNotNull();
        assertThat(itemJpaEntity.getId()).isNull();
        assertThat(itemJpaEntity.getName()).isEqualTo(설이.getItemDetail().getName());
        assertThat(itemJpaEntity.getShape().getX()).isEqualTo(설이.getShape().getX());
        assertThat(itemJpaEntity.getShape().getY()).isEqualTo(설이.getShape().getX());
        assertThat(((ItemShapeRectangleJpaEntity)itemJpaEntity.getShape()).getWidth()).isEqualTo(((Rectangle) 설이.getShape()).getWidth());
        assertThat(((ItemShapeRectangleJpaEntity)itemJpaEntity.getShape()).getHeight()).isEqualTo(((Rectangle) 설이.getShape()).getHeight());
        assertThat(((ItemShapeRectangleJpaEntity)itemJpaEntity.getShape()).getRotation()).isEqualTo(((Rectangle) 설이.getShape()).getRotation());
    }

    @Test
    @DisplayName("아이템 엔티티 도메인 => JPA 변환 테스트(원형)")
    void testMapToNewJpaEntity_Circle() throws Exception {
        // given
        Item 강아지 = MockEntityFactoryService.getCircleItem();

        // when
        ItemJpaEntity itemJpaEntity = sut.mapToNewJpaEntity(강아지, null, null, null);

        // then
        assertThat(itemJpaEntity).isNotNull();
        assertThat(itemJpaEntity.getId()).isNull();
        assertThat(itemJpaEntity.getName()).isEqualTo(강아지.getItemDetail().getName());
        assertThat(itemJpaEntity.getShape().getX()).isEqualTo(강아지.getShape().getX());
        assertThat(itemJpaEntity.getShape().getY()).isEqualTo(강아지.getShape().getX());
        assertThat(((ItemShapeCircleJpaEntity) itemJpaEntity.getShape()).getRadius()).isEqualTo(((Circle) 강아지.getShape()).getRadius());
    }

    @Test
    @DisplayName("아이템 엔티티 도메인 => JPA 변환 테스트(타원형)")
    void testMapToNewJpaEntity_Ellipse() throws Exception {
        // given
        Item 화분 = MockEntityFactoryService.getEllipseItem();

        // when
        ItemJpaEntity itemJpaEntity = sut.mapToNewJpaEntity(화분, null, null, null);

        // then
        assertThat(itemJpaEntity).isNotNull();
        assertThat(itemJpaEntity.getId()).isNull();
        assertThat(itemJpaEntity.getName()).isEqualTo(화분.getItemDetail().getName());
        assertThat(itemJpaEntity.getShape().getX()).isEqualTo(화분.getShape().getX());
        assertThat(itemJpaEntity.getShape().getY()).isEqualTo(화분.getShape().getX());
        assertThat(((ItemShapeEllipseJpaEntity) itemJpaEntity.getShape()).getRadiusX()).isEqualTo(((Ellipse) 화분.getShape()).getRadiusX());
        assertThat(((ItemShapeEllipseJpaEntity) itemJpaEntity.getShape()).getRadiusY()).isEqualTo(((Ellipse) 화분.getShape()).getRadiusY());
        assertThat(((ItemShapeEllipseJpaEntity) itemJpaEntity.getShape()).getRotation()).isEqualTo(((Ellipse) 화분.getShape()).getRotation());
    }

}