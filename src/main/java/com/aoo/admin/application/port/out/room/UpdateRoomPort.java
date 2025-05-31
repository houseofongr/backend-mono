package com.aoo.admin.application.port.out.room;

import com.aoo.admin.domain.house.room.Room;

import java.util.List;

public interface UpdateRoomPort {
    int update(List<Room> rooms);
}
