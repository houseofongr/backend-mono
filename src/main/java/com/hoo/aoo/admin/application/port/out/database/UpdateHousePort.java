package com.hoo.aoo.admin.application.port.out.database;

import com.hoo.aoo.admin.domain.house.House;

import java.util.Map;

public interface UpdateHousePort {
    void update(House house, Map<String, Long> imageIdMap);
}
