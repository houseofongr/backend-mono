package com.aoo.common.adapter.out.persistence.repository;

import com.aoo.admin.application.port.in.house.QueryHouseListCommand;
import com.aoo.common.adapter.out.persistence.entity.HouseJpaEntity;
import org.springframework.data.domain.Page;

public interface HouseQueryDslRepository {
    Page<HouseJpaEntity> searchByCommand(QueryHouseListCommand command);
}
