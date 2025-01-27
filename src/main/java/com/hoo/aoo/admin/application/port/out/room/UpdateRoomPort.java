package com.hoo.aoo.admin.application.port.out.room;

import com.hoo.aoo.admin.domain.room.Room;

import java.util.List;

public interface UpdateRoomPort {
    int update(List<Room> rooms);
}
