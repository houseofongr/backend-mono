package com.aoo.admin.application.port.in.category;

import com.aoo.common.adapter.out.persistence.entity.CategoryJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateCategoryResultTest {

    @Test
    @DisplayName("카테고리 수정 메시지 테스트")
    void testMessage() {
        // given
        CategoryJpaEntity changedCategory = new CategoryJpaEntity(1L, "new category");

        // when
        UpdateCategoryResult result = UpdateCategoryResult.of(changedCategory);

        // then
        assertThat(result.message()).matches("\\[#\\d+]번 카테고리가 수정되었습니다.");
    }

}