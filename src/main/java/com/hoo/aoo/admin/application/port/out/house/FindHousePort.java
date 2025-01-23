package com.hoo.aoo.admin.application.port.out.house;

import com.hoo.aoo.admin.application.port.in.house.QueryHouseListCommand;
import com.hoo.aoo.admin.application.port.in.house.QueryHouseListResult;
import com.hoo.aoo.admin.application.port.in.house.QueryHouseResult;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.house.House;

import java.util.Optional;

public interface FindHousePort {
    Optional<House> load(Long id) throws AreaLimitExceededException, AxisLimitExceededException;
    Optional<QueryHouseResult> findResult(Long id);
    QueryHouseListResult search(QueryHouseListCommand command);
}
