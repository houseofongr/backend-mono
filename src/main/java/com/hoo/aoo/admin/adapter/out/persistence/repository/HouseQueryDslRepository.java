package com.hoo.aoo.admin.adapter.out.persistence.repository;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.application.port.in.ReadHouseListCommand;
import org.springframework.data.domain.Page;

import java.util.List;

public interface HouseQueryDslRepository {
    Page<HouseJpaEntity> searchByCommand(ReadHouseListCommand command);
}
