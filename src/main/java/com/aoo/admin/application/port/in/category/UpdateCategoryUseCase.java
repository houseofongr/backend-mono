package com.aoo.admin.application.port.in.category;

public interface UpdateCategoryUseCase {
    UpdateCategoryResult update(Long categoryId, String name);
}
