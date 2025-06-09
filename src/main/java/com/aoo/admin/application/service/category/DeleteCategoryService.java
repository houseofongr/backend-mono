package com.aoo.admin.application.service.category;

import com.aoo.admin.application.port.in.category.DeleteCategoryResult;
import com.aoo.admin.application.port.in.category.DeleteCategoryUseCase;
import com.aoo.admin.application.port.out.category.DeleteCategoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteCategoryService implements DeleteCategoryUseCase {

    private final DeleteCategoryPort deleteCategoryPort;

    @Override
    public DeleteCategoryResult delete(Long categoryId) {
        return deleteCategoryPort.delete(categoryId);
    }
}
