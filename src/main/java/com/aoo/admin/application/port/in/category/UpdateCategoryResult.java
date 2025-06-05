package com.aoo.admin.application.port.in.category;

import com.aoo.common.adapter.out.persistence.entity.CategoryJpaEntity;

public record UpdateCategoryResult(
        String message,
        Long categoryId,
        String name
) {
    public static UpdateCategoryResult of(CategoryJpaEntity updatedCategory) {
        return new UpdateCategoryResult(
                String.format("[#%d]번 카테고리가 수정되었습니다.", updatedCategory.getId()),
                updatedCategory.getId(),
                updatedCategory.getName()
        );
    }
}
