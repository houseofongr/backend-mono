package com.aoo.admin.application.port.out.category;

import com.aoo.admin.application.port.in.category.DeleteCategoryResult;

public interface DeleteCategoryPort {
    DeleteCategoryResult delete(Long categoryId);
}
