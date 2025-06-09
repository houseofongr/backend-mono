package com.aoo.admin.application.port.out.house;

import com.aoo.admin.application.port.in.house.QueryHouseListCommand;
import com.aoo.admin.application.port.in.house.QueryHouseListResult;
import com.aoo.admin.domain.house.House;

import java.util.Optional;

public interface FindHousePort {
    Optional<House> load(Long id);

    QueryHouseListResult search(QueryHouseListCommand command);
}
