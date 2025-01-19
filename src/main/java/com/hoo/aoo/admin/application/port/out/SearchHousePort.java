package com.hoo.aoo.admin.application.port.out;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.application.port.in.house.QueryHouseListCommand;
import org.springframework.data.domain.Page;

public interface SearchHousePort {
    Page<HouseJpaEntity> search(QueryHouseListCommand command);
}
