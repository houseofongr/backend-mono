package com.hoo.aoo.admin.application.port.out.house;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.application.port.in.house.QueryHouseListCommand;
import com.hoo.aoo.admin.application.port.in.house.QueryHouseListResult;
import org.springframework.data.domain.Page;

public interface SearchHousePort {
    QueryHouseListResult search(QueryHouseListCommand command);
}
