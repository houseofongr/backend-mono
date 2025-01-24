package com.hoo.aoo.admin.application.port.out.house;

import com.hoo.aoo.admin.domain.house.House;

public interface SaveHousePort {
    Long save(House house);
}
