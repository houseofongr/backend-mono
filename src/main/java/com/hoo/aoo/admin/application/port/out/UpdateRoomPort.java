package com.hoo.aoo.admin.application.port.out;

import com.hoo.aoo.admin.application.port.in.house.UpdateRoomInfoCommand;

public interface UpdateRoomPort {
    int update(UpdateRoomInfoCommand command);
}
