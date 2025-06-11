package com.aoo.admin.application.port.in.category;

import java.util.List;

public record SearchCategoryResult(
        List<CategoryInfo> categories
) {

    public record CategoryInfo(
            Long id,
            String kor,
            String eng
    ) {
    }
}
