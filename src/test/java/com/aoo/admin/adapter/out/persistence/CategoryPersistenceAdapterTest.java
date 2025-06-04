package com.aoo.admin.adapter.out.persistence;

import com.aoo.admin.application.port.in.category.CreateCategoryResult;
import com.aoo.common.adapter.out.persistence.PersistenceAdapterTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@PersistenceAdapterTest
@Import(CategoryPersistenceAdapter.class)
class CategoryPersistenceAdapterTest {

    @Autowired
    CategoryPersistenceAdapter sut;

    @Test
    @DisplayName("카테고리 생성 테스트")
    void testCreateCategory() {
        // given
        String name = "new category";

        // when
        CreateCategoryResult result = sut.save(name);

        // then
        assertThat(result.categoryId()).isNotNull();
        assertThat(result.name()).isEqualTo(name);
    }
}