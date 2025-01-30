package com.hoo.aoo.admin.application.port.in.room;

import com.hoo.aoo.admin.domain.room.Room;

public record QueryRoomResult(
        String name,
        Float width,
        Float height,
        Long imageId
) {

    public static QueryRoomResult of(Room room) {
        return new QueryRoomResult(
                room.getRoomDetail().getName(),
                room.getArea().getWidth(),
                room.getArea().getHeight(),
                room.getImageFile().getFileId().getId()
        );
    }
}
