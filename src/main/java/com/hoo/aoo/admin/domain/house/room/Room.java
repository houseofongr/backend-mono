package com.hoo.aoo.admin.domain.house.room;

import com.hoo.aoo.admin.domain.Area;
import com.hoo.aoo.admin.domain.Axis;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.house.HouseId;
import lombok.Getter;

@Getter
public class Room {

    private final RoomId id;
    private final Area area;
    private final Axis axis;

    private Room(RoomId id, Area area, Axis axis) {
        this.area = area;
        this.id = id;
        this.axis = axis;
    }

    public static Room create(HouseId houseId, String name, Float x, Float y, Float z, Float width, Float height) throws AxisLimitExceededException, AreaLimitExceededException {

        RoomId roomId = new RoomId(houseId, name);
        Axis axis = new Axis(x, y, z);
        Area area = new Area(width, height);

        return new Room(roomId, area, axis);
    }

    public static Room load(HouseId houseId, String name, Float x, Float y, Float z, Float width, Float height) throws AreaLimitExceededException, AxisLimitExceededException {

        RoomId roomId = new RoomId(houseId, name);
        Axis axis = new Axis(x, y, z);
        Area area = new Area(width, height);

        return new Room(roomId, area, axis);
    }

    public void updateInfo(String name) {
        id.update(name);
    }
}
