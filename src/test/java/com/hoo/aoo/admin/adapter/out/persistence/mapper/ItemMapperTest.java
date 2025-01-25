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

    ItemMapper sut = new ItemMapper();

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
        assertThat(soundSourceJpaEntity.getName()).isEqualTo(soundSource.getDetail().getName());
        assertThat(soundSourceJpaEntity.getDescription()).isEqualTo(soundSource.getDetail().getDescription());
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
        assertThat(itemJpaEntity.getName()).isEqualTo(설이.getItemName().getName());
        assertThat(((RectangleItemJpaEntity) itemJpaEntity).getX()).isEqualTo(설이.getShape().getX());
        assertThat(((RectangleItemJpaEntity) itemJpaEntity).getY()).isEqualTo(설이.getShape().getX());
        assertThat(((RectangleItemJpaEntity) itemJpaEntity).getWidth()).isEqualTo(((Rectangle) 설이.getShape()).getWidth());
        assertThat(((RectangleItemJpaEntity) itemJpaEntity).getHeight()).isEqualTo(((Rectangle) 설이.getShape()).getHeight());
        assertThat(((RectangleItemJpaEntity) itemJpaEntity).getRotation()).isEqualTo(((Rectangle) 설이.getShape()).getRotation());
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
        assertThat(itemJpaEntity.getName()).isEqualTo(강아지.getItemName().getName());
        assertThat(((CircleItemJpaEntity) itemJpaEntity).getX()).isEqualTo(강아지.getShape().getX());
        assertThat(((CircleItemJpaEntity) itemJpaEntity).getY()).isEqualTo(강아지.getShape().getX());
        assertThat(((CircleItemJpaEntity) itemJpaEntity).getRadius()).isEqualTo(((Circle) 강아지.getShape()).getRadius());
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
        assertThat(itemJpaEntity.getName()).isEqualTo(화분.getItemName().getName());
        assertThat(((EllipseItemJpaEntity) itemJpaEntity).getX()).isEqualTo(화분.getShape().getX());
        assertThat(((EllipseItemJpaEntity) itemJpaEntity).getY()).isEqualTo(화분.getShape().getX());
        assertThat(((EllipseItemJpaEntity) itemJpaEntity).getRadiusX()).isEqualTo(((Ellipse) 화분.getShape()).getRadiusX());
        assertThat(((EllipseItemJpaEntity) itemJpaEntity).getRadiusY()).isEqualTo(((Ellipse) 화분.getShape()).getRadiusY());
        assertThat(((EllipseItemJpaEntity) itemJpaEntity).getRotation()).isEqualTo(((Ellipse) 화분.getShape()).getRotation());
    }

}