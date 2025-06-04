package com.aoo.admin.application.port.in.category;

import com.aoo.common.adapter.out.persistence.entity.CategoryJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreateCategoryResultTest {

    @Test
    @DisplayName("카테고리 생성 메시지 테스트")
    void testMessage() {
        // given
        CategoryJpaEntity newCategory = new CategoryJpaEntity(1L, "new category");

        // when
        CreateCategoryResult result = CreateCategoryResult.of(newCategory);

        // then
        assertThat(result.message()).matches("\\[#\\d+]번 카테고리가 생성되었습니다.");
    }

}