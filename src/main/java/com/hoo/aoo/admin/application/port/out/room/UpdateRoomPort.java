package com.hoo.aoo.admin.application.port.out.room;

import com.hoo.aoo.admin.application.port.in.room.UpdateRoomInfoCommand;

public interface UpdateRoomPort {
    int update(UpdateRoomInfoCommand command);
}
