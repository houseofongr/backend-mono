package com.hoo.aoo.admin.application.port.out.database;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.application.port.in.ReadHouseListCommand;
import org.springframework.data.domain.Page;

public interface QueryHousePort {
    Page<HouseJpaEntity> query(ReadHouseListCommand command);
}
