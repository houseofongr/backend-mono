package com.hoo.aoo.admin.application.port.out;

import com.hoo.aoo.admin.domain.house.House;

public interface UpdateHousePort {
    void update(Long houseId, House house);
}
