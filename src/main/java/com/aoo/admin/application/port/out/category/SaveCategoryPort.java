package com.aoo.admin.application.port.out.category;

import com.aoo.admin.application.port.in.category.CreateCategoryResult;

public interface SaveCategoryPort {
    CreateCategoryResult save(String kor, String eng);
}
