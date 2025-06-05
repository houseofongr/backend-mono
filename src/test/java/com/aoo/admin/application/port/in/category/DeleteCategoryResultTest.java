package com.aoo.admin.application.port.in.category;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeleteCategoryResultTest {

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