package com.hoo.aoo.admin.application.port.out.database;

import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.house.room.Room;

import java.util.List;
import java.util.Map;

public interface UpdateHousePort {
    void update(House house, List<Room> rooms, Map<Object, Long> imageIdMap);
}
