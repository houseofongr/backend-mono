package com.aoo.aar.application.port.in.universe;

import org.springframework.data.domain.Pageable;

public record SearchPublicUniverseCommand(
        Pageable pageable,
        Long userId,
        String searchType,
        String keyword,
        String sortType,
        Boolean isAsc
) {
}
