package com.aoo.admin.adapter.out.persistence;

import com.aoo.admin.application.port.in.category.CreateCategoryResult;
import com.aoo.admin.application.port.out.category.SaveCategoryPort;
import com.aoo.common.adapter.out.persistence.entity.CategoryJpaEntity;
import com.aoo.common.adapter.out.persistence.repository.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryPersistenceAdapter implements SaveCategoryPort {

    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public CreateCategoryResult save(String name) {
        CategoryJpaEntity newCategory = CategoryJpaEntity.create(name);
        categoryJpaRepository.save(newCategory);

        return CreateCategoryResult.of(newCategory);
    }
}
