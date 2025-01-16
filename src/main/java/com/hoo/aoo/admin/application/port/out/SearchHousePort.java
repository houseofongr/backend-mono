package com.hoo.aoo.admin.application.port.out;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.application.port.in.QueryHouseListCommand;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface SearchHousePort {
    Page<HouseJpaEntity> search(QueryHouseListCommand command);
}
