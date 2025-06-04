package com.aoo.admin.application.port.in.category;

import com.aoo.common.adapter.out.persistence.entity.CategoryJpaEntity;

import java.util.List;

public record SearchCategoryResult(
        List<CategoryInfo> categories
) {
    public static SearchCategoryResult of(List<CategoryJpaEntity> categoryJpaEntities) {
        return new SearchCategoryResult(categoryJpaEntities.stream().map(CategoryInfo::of).toList());
    }

    public record CategoryInfo(
            Long id,
            String name
    ) {

        public static CategoryInfo of(CategoryJpaEntity categoryJpaEntity) {
            return new CategoryInfo(categoryJpaEntity.getId(), categoryJpaEntity.getName());
        }
    }
}
