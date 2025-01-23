package com.hoo.aoo.admin.domain.item;

import com.hoo.aoo.admin.domain.file.FileId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ItemTest {

    @Test
    @DisplayName("아이템 생성 테스트")
    void testCreateItem() {
        // given
        Long itemId = 1L;
        String itemName = "설이";
        Shape rectangle = new Rectangle(100f, 100f, 10f, 10f, 5f);
        List<Long> fileIds = List.of(1L, 2L);

        // when
        Item item = Item.create(itemId, itemName, rectangle, fileIds);

        // then
        assertThat(item).isNotNull();
        assertThat(item.getItemId().getId()).isEqualTo(itemId);
        assertThat(item.getItemName().getName()).isEqualTo(itemName);
        assertThat(item.getShape()).isEqualTo(rectangle);
        assertThat(item.getAudioFileIds().stream().map(FileId::getId)).contains(1L, 2L);
    }
}