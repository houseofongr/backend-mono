package com.hoo.aoo.admin.application.port.in;

import org.springframework.data.domain.Pageable;

public record ReadHouseListCommand(
        Pageable pageable,
        String searchType,
        String keyword
) {
}
