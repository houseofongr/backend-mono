package com.hoo.aoo.admin.application.port.out.database;

import com.hoo.aoo.admin.domain.house.House;

public interface UpdateHousePort {
    void update(Long houseId, House house);
}
