package com.aoo.admin.adapter.out.persistence.mapper;

import com.aoo.admin.application.port.in.category.CreateCategoryResult;
import com.aoo.admin.application.port.in.category.DeleteCategoryResult;
import com.aoo.admin.application.port.in.category.UpdateCategoryResult;
import com.aoo.common.adapter.out.persistence.entity.CategoryJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryMapperTest {

    CategoryMapper sut = new CategoryMapper();

    @Test
    @DisplayName("카테고리 생성 메시지 테스트")
    void testCreateMessage() {
        // given
        CategoryJpaEntity newCategory = new CategoryJpaEntity(1L, "새 카테고리", "new category");

        // when
        CreateCategoryResult result = sut.mapToCreateCategoryResult(newCategory);

        // then
        assertThat(result.message()).matches("\\[#\\d+]번 카테고리가 생성되었습니다.");
    }

    @Test
    @DisplayName("카테고리 수정 메시지 테스트")
    void testUpdateMessage() {
        // given
        CategoryJpaEntity changedCategory = new CategoryJpaEntity(1L, "새 카테고리",  "new category");

        // when
        UpdateCategoryResult result = sut.mapToUpdateCategoryResult(changedCategory);

        // then
        assertThat(result.message()).matches("\\[#\\d+]번 카테고리가 수정되었습니다.");
    }

    @Test
    @DisplayName("카테고리 삭제 메시지")
    void testDeleteCategoryMessage() {
        // given
        Long id = 1L;

        // when
        DeleteCategoryResult result = DeleteCategoryResult.of(id);

        // then
        assertThat(result.message()).matches("\\[#\\d]번 카테고리가 삭제되었습니다.");
    }

}