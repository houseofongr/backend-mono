package com.hoo.aoo.admin.domain.item;

import com.hoo.aoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ItemTest {

    @Test
    @DisplayName("아이템 생성 테스트")
    void testCreateItem() {
        // given
        Long roomId = 1L;
        Long userId = 10L;
        String itemName = "설이";
        Shape rectangle = new Rectangle(100f, 100f, 10f, 10f, 5f);

        // when
        Item item = Item.create(1L, userId, roomId, itemName, rectangle);

        // then
        assertThat(item).isNotNull();
        assertThat(item.getRoomId().getId()).isEqualTo(roomId);
        assertThat(item.getItemDetail().getName()).isEqualTo(itemName);
        assertThat(item.getShape()).isEqualTo(rectangle);
    }

    @Test
    @DisplayName("아이템 수정 테스트")
    void testUpdateItem() throws Exception {
        // given
        String newName = "고양이";
        Shape rectangle = new Rectangle(300f, 300f, 20f, 20f, 10f);

        Item 설이 = MockEntityFactoryService.getRectangleItem();
        Item 설이2 = MockEntityFactoryService.getRectangleItem();

        // when
        설이.update(newName, null);
        설이2.update(null,rectangle);

        // then
        assertThat(설이.getItemDetail().getName()).isEqualTo("고양이");
        assertThat(설이.getShape()).usingRecursiveComparison().isEqualTo(MockEntityFactoryService.getRectangleItem().getShape());

        assertThat(설이2.getItemDetail().getName()).isEqualTo("설이");
        assertThat(설이2.getShape()).isEqualTo(rectangle);
    }

    @Test
    @DisplayName("아이템 음원 보유여부 테스트")
    void testHasSoundSource() throws Exception {
        // given
        Item rectangleItem = MockEntityFactoryService.getRectangleItem();
        Item rectangleItemWithSoundSource = MockEntityFactoryService.loadRectangleItem();

        // when
        boolean hasSoundSource1 = rectangleItem.hasSoundSource();
        boolean hasSoundSource2 = rectangleItemWithSoundSource.hasSoundSource();

        // then
        assertThat(hasSoundSource1).isFalse();
        assertThat(hasSoundSource2).isTrue();
    }
}