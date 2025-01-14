package com.hoo.aoo.admin.application.port.out.database;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.application.port.in.ReadHouseListCommand;

import java.util.List;

public interface QueryHousePort {
    List<HouseJpaEntity> query(ReadHouseListCommand command);
}
