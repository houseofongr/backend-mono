package com.hoo.aoo.admin.domain.item;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ItemTest {

    @Test
    @DisplayName("아이템 생성 테스트")
    void testCreateItem() {
        // given
        Long roomId = 1L;
        String itemName = "설이";
        Shape rectangle = new Rectangle(100f, 100f, 10f, 10f, 5f);

        // when
        Item item = Item.create(1L, roomId, itemName, rectangle);

        // then
        assertThat(item).isNotNull();
        assertThat(item.getRoomId().getId()).isEqualTo(roomId);
        assertThat(item.getItemName().getName()).isEqualTo(itemName);
        assertThat(item.getShape()).isEqualTo(rectangle);
    }
}