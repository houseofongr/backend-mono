package com.aoo.admin.application.port.in.house;

import com.aoo.common.adapter.out.persistence.condition.HouseSearchType;
import org.springframework.data.domain.Pageable;

public record QueryHouseListCommand(
        Pageable pageable,
        HouseSearchType searchType,
        String keyword
) {
}
