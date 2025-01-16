package com.hoo.aoo.admin.application.port.out;

import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.house.House;

import java.util.Optional;

public interface FindHousePort {
    Optional<House> load(Long id) throws AreaLimitExceededException, AxisLimitExceededException;
}
