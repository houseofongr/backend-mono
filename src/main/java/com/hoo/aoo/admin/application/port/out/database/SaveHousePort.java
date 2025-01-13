package com.hoo.aoo.admin.application.port.out.database;

import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.room.Room;

import java.util.List;

public interface SaveHousePort {
    Long save(House house, List<Room> rooms);
}
