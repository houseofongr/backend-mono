package com.hoo.aoo.admin.application.port.out.house;

import com.hoo.aoo.admin.domain.house.room.Room;

public interface CreateRoomPort {
    Room createRoom(String name, Float x, Float y, Float z, Float width, Float height, Long imageFileId);
}
