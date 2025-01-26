package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.admin.adapter.out.persistence.entity.ItemJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.mapper.ItemMapper;
import com.hoo.aoo.admin.adapter.out.persistence.mapper.SoundSourceMapper;
import com.hoo.aoo.admin.adapter.out.persistence.repository.ItemJpaRepository;
import com.hoo.aoo.admin.domain.item.Item;
import com.hoo.aoo.common.adapter.out.persistence.PersistenceAdapterTest;
import com.hoo.aoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@PersistenceAdapterTest
@Import({ItemPersistenceAdapter.class, ItemMapper.class, SoundSourceMapper.class})
class ItemPersistenceAdapterTest {

    @Autowired
    ItemPersistenceAdapter sut;
    @Autowired
    private ItemJpaRepository itemJpaRepository;

    @Test
    @Sql("ItemPersistenceAdapterTest.sql")
    @DisplayName("아이템 저장 테스트")
    void testSaveItem() throws Exception {
        // given
        List<Item> items = List.of(MockEntityFactoryService.getCircleItem(), MockEntityFactoryService.getEllipseItem(), MockEntityFactoryService.getRectangleItem());

        // when
        List<Long> savedItemId = sut.save(10L, 1L, 1L, items);

        // then
        assertThat(savedItemId).hasSize(3);
    }

    @Test
    @Sql("ItemPersistenceAdapterTest2.sql")
    @DisplayName("아이템 조회 테스트")
    void testFindItem() {
        // given

        // when
        Optional<Item> item1 = sut.load(1L);
        Optional<Item> item2 = sut.load(2L);
        Optional<Item> item3 = sut.load(3L);

        // then
        assertThat(item1).isNotEmpty();
        assertThat(item2).isNotEmpty();
        assertThat(item3).isNotEmpty();

        assertThat(item1.get().getItemDetail().getName()).isEqualTo("설이");
        assertThat(item2.get().getItemDetail().getName()).isEqualTo("강아지");
        assertThat(item3.get().getItemDetail().getName()).isEqualTo("화분");

        assertThat(item1.get().getSoundSources()).anySatisfy(soundSource ->
            assertThat(soundSource.getSoundSourceDetail().getName()).isEqualTo("골골송")
        );
    }

    @Test
    @Sql("ItemPersistenceAdapterTest2.sql")
    @DisplayName("아이템 삭제 테스트")
    void testDeleteItem() {
        // given
        Long id = 2L;

        // when
        sut.deleteItem(id);

        // then
        assertThat(itemJpaRepository.findById(id)).isEmpty();
    }
}