package com.hoo.aoo.admin.application.port.in.house;

import java.util.List;

public record UpdateRoomInfoCommand(
        List<RoomInfo> roomInfos
) {
    public record RoomInfo(
            Long roomId,
            String newName
    ) {

    }
}
