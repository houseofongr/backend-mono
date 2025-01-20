package com.hoo.aoo.admin.application.port.out.house;

import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.house.room.Room;

import java.util.List;
import java.util.Map;

public interface SaveHousePort {
    Long save(House house, List<Room> rooms, Map<String, Long> imageFileIdMap);
}
