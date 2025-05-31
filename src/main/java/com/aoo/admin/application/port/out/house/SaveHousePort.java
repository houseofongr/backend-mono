package com.aoo.admin.application.port.out.house;

import com.aoo.admin.domain.house.House;

public interface SaveHousePort {
    Long save(House house);
}
