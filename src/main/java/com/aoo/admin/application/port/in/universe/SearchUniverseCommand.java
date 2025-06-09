package com.aoo.admin.application.port.in.universe;

import org.springframework.data.domain.Pageable;

public record SearchUniverseCommand(
        Pageable pageable,
        String searchType,
        String keyword,
        String category,
        String sortType,
        Boolean isAsc
) {
}
