package com.hoo.aoo.admin.application.port.in.universe;

import com.hoo.aoo.common.adapter.out.persistence.condition.UniverseSearchType;
import com.hoo.aoo.common.adapter.out.persistence.condition.UniverseSortType;
import org.springframework.data.domain.Pageable;

public record SearchUniverseCommand(
        Pageable pageable,
        UniverseSortType sortType,
        Boolean isAsc,
        UniverseSearchType searchType,
        String keyword
) {
}
