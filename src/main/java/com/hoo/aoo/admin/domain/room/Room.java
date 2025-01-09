package com.hoo.aoo.admin.domain.room;

import com.hoo.aoo.admin.domain.Area;
import com.hoo.aoo.admin.domain.Axis;
import lombok.Getter;

@Getter
public class Room {

    private final RoomId id;
    private final Area area;
    private final Axis axis;
    private final RoomImage image;

    private Room(RoomId id, Area area, Axis axis, RoomImage image) {
        this.area = area;
        this.id = id;
        this.axis = axis;
        this.image = image;
    }

    public static Room createNewRoom(RoomId roomId, Axis axis, Area area, RoomImage image) {
        return new Room(roomId, area, axis, image);
    }
}
