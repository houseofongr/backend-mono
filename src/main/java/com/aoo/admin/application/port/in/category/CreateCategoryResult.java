package com.aoo.admin.application.port.in.category;

import com.aoo.common.adapter.out.persistence.entity.CategoryJpaEntity;

public record CreateCategoryResult(
        String message,
        Long categoryId,
        String name
) {
    public static CreateCategoryResult of(CategoryJpaEntity newCategory) {
        return new CreateCategoryResult(
                String.format("[#%d]번 카테고리가 생성되었습니다.", newCategory.getId()),
                newCategory.getId(),
                newCategory.getName()
        );
    }
}
