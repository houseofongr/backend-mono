package com.hoo.aoo.admin.domain.item;

import com.hoo.aoo.admin.domain.file.FileId;
import com.hoo.aoo.admin.domain.item.soundsource.SoundSource;
import com.hoo.aoo.common.FixtureRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ItemTest {

    @Test
    @DisplayName("아이템 생성 테스트")
    void testCreateItem() {
        // given
        Long roomId = 1L;
        String itemName = "설이";
        Shape rectangle = new Rectangle(100f, 100f, 10f, 10f, 5f);
        List<SoundSource> soundSources = List.of(FixtureRepository.getSoundSource(), FixtureRepository.getSoundSource());

        // when
        Item item = Item.create(roomId, itemName, rectangle, soundSources);

        // then
        assertThat(item).isNotNull();
        assertThat(item.getItemId().getRoomId()).isEqualTo(roomId);
        assertThat(item.getItemId().getName()).isEqualTo(itemName);
        assertThat(item.getShape()).isEqualTo(rectangle);
        assertThat(item.getSoundSources()).hasSize(2);
    }
}