package com.aoo.admin.adapter.out.persistence;

import com.aoo.admin.application.port.in.category.CreateCategoryResult;
import com.aoo.admin.application.port.in.category.SearchCategoryResult;
import com.aoo.admin.application.port.in.category.UpdateCategoryResult;
import com.aoo.admin.application.port.out.category.FindCategoryPort;
import com.aoo.admin.application.port.out.category.SaveCategoryPort;
import com.aoo.admin.application.port.out.category.UpdateCategoryPort;
import com.aoo.admin.application.service.AdminErrorCode;
import com.aoo.admin.application.service.AdminException;
import com.aoo.common.adapter.out.persistence.entity.CategoryJpaEntity;
import com.aoo.common.adapter.out.persistence.repository.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryPersistenceAdapter implements SaveCategoryPort, FindCategoryPort, UpdateCategoryPort {

    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public CreateCategoryResult save(String name) {
        CategoryJpaEntity newCategory = CategoryJpaEntity.create(name);
        categoryJpaRepository.save(newCategory);

        return CreateCategoryResult.of(newCategory);
    }

    @Override
    public SearchCategoryResult findAll() {
        return SearchCategoryResult.of(categoryJpaRepository.findAll());
    }

    @Override
    public UpdateCategoryResult update(Long categoryId, String name) {
        CategoryJpaEntity targetCategory = categoryJpaRepository.findById(categoryId)
                .orElseThrow(() -> new AdminException(AdminErrorCode.CATEGORY_NOT_FOUND));

        targetCategory.update(name);

        return UpdateCategoryResult.of(targetCategory);
    }
}
