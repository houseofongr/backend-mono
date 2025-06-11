package com.aoo.admin.application.port.in.category;

public record UpdateCategoryResult(
        String message,
        Long categoryId,
        String kor,
        String eng
) {
}
