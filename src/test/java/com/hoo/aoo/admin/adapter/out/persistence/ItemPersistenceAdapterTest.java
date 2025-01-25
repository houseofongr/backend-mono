package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.admin.adapter.out.persistence.mapper.ItemMapper;
import com.hoo.aoo.admin.domain.item.Item;
import com.hoo.aoo.common.adapter.out.persistence.PersistenceAdapterTest;
import com.hoo.aoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@PersistenceAdapterTest
@Import({ItemPersistenceAdapter.class, ItemMapper.class})
class ItemPersistenceAdapterTest {

    @Autowired
    ItemPersistenceAdapter sut;

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

}