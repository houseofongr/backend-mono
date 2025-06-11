package com.aoo.admin.application.port.in.category;

public record CreateCategoryResult(
        String message,
        Long categoryId,
        String kor,
        String eng
) {
}
