package com.hoo.aoo.admin.application.port.out;

import com.hoo.aoo.admin.domain.house.House;

import java.util.Optional;

public interface LoadHousePort {
    Optional<House> load(Long houseId);
}
