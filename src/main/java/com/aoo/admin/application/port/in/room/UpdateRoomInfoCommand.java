package com.aoo.admin.application.port.in.room;

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
