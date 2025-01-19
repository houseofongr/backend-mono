package com.hoo.aoo.admin.adapter.out.persistence.repository;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.application.port.in.house.QueryHouseListCommand;
import org.springframework.data.domain.Page;

public interface HouseQueryDslRepository {
    Page<HouseJpaEntity> searchByCommand(QueryHouseListCommand command);
}
