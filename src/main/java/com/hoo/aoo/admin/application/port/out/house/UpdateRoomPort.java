package com.hoo.aoo.admin.application.port.out.house;

import com.hoo.aoo.admin.application.port.in.house.UpdateRoomInfoCommand;

public interface UpdateRoomPort {
    int update(UpdateRoomInfoCommand command);
}
