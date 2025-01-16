package com.hoo.aoo.admin.application.port.in;

import com.hoo.aoo.admin.adapter.out.persistence.SearchType;
import org.springframework.data.domain.Pageable;

public record QueryHouseListCommand(
        Pageable pageable,
        SearchType searchType,
        String keyword
) {
}
