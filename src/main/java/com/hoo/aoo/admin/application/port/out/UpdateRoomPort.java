package com.hoo.aoo.admin.application.port.out;

import com.hoo.aoo.admin.domain.house.room.Room;

public interface UpdateRoomPort {
    void update(Long houseId, String originalName, Room room);
}
