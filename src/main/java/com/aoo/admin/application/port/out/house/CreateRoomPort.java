package com.aoo.admin.application.port.out.house;

import com.aoo.admin.domain.house.room.Room;

public interface CreateRoomPort {
    Room createRoom(String name, Float x, Float y, Float z, Float width, Float height, Long imageFileId);
}
