package com.aoo.admin.adapter.out.persistence;

import com.aoo.admin.application.port.in.category.CreateCategoryResult;
import com.aoo.admin.application.port.in.category.SearchCategoryResult;
import com.aoo.admin.application.port.in.category.UpdateCategoryResult;
import com.aoo.common.adapter.out.persistence.PersistenceAdapterTest;
import com.aoo.common.adapter.out.persistence.entity.CategoryJpaEntity;
import com.aoo.common.adapter.out.persistence.repository.CategoryJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@PersistenceAdapterTest
@Sql("CategoryPersistenceAdapterTest.sql")
@Import(CategoryPersistenceAdapter.class)
class CategoryPersistenceAdapterTest {

    @Autowired
    CategoryJpaRepository categoryJpaRepository;

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

    @Test
    @DisplayName("카테고리 검색 테스트")
    void testFindAllCategory() {

        // when
        SearchCategoryResult result = sut.findAll();

        // then
        assertThat(result.categories())
                .anyMatch(categoryInfo -> categoryInfo.id().equals(1L) && categoryInfo.name().equalsIgnoreCase("Life"))
                .anyMatch(categoryInfo -> categoryInfo.id().equals(2L) && categoryInfo.name().equalsIgnoreCase("Public"))
                .anyMatch(categoryInfo -> categoryInfo.id().equals(3L) && categoryInfo.name().equalsIgnoreCase("Government"));
    }

    @Test
    @DisplayName("카테고리 수정 테스트")
    void testUpdateCategory() {
        // given
        Long id = 1L;
        String name = "수정된 카테고리";

        // when
        UpdateCategoryResult result = sut.update(id, name);
        CategoryJpaEntity categoryJpaEntity = categoryJpaRepository.findById(1L).orElseThrow();

        // then
        assertThat(result.categoryId()).isEqualTo(id);
        assertThat(result.name()).isEqualTo(name);
        assertThat(categoryJpaEntity.getName()).isEqualTo(name);
    }
}