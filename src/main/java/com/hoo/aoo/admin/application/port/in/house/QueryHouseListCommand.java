package com.hoo.aoo.admin.application.port.in.house;

import com.hoo.aoo.common.adapter.out.persistence.condition.HouseSearchType;
import org.springframework.data.domain.Pageable;

public record QueryHouseListCommand(
        Pageable pageable,
        HouseSearchType searchType,
        String keyword
) {
}
