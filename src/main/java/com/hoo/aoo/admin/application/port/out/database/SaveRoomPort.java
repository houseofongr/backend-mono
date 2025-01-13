package com.hoo.aoo.admin.application.port.out.database;

import com.hoo.aoo.admin.domain.room.Room;

public interface SaveRoomPort {
    Long save(Room room);
}
