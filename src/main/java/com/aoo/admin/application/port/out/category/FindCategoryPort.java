package com.aoo.admin.application.port.out.category;

import com.aoo.admin.application.port.in.category.SearchCategoryResult;
import com.aoo.admin.domain.universe.UniverseCategory;

public interface FindCategoryPort {
    SearchCategoryResult findAll();
    UniverseCategory findUniverseCategory(Long categoryId);
}
