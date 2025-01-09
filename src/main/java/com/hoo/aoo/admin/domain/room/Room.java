package com.hoo.aoo.admin.domain.room;

import com.hoo.aoo.admin.domain.Axis;
import lombok.Getter;

@Getter
public class Room {

    private final RoomId id;
    private final RoomArea area;
    private final RoomImage image;
    private final Axis axis;

    public Room(RoomArea area, RoomId id, RoomImage image, Axis axis) {
        this.area = area;
        this.id = id;
        this.image = image;
        this.axis = axis;
    }
}
