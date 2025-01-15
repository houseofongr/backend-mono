package com.hoo.aoo.admin.domain.room;

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
    private final RoomImage image;

    private Room(RoomId id, Area area, Axis axis, RoomImage image) {
        this.area = area;
        this.id = id;
        this.axis = axis;
        this.image = image;
    }

    public static Room create(HouseId houseId, String name, Float x, Float y, Float z, Float width, Float height, Long imageId) throws AxisLimitExceededException, AreaLimitExceededException {

        RoomId roomId = new RoomId(houseId, name);
        Axis axis = new Axis(x, y, z);
        Area area = new Area(width, height);
        RoomImage image = new RoomImage(imageId);

        return new Room(roomId, area, axis, image);
    }

    public void update(String name, Float x, Float y, Float z, Float width, Float height) throws AreaLimitExceededException, AxisLimitExceededException {
        id.update(name);
        axis.update(x, y, z);
        area.update(width,height);
    }
}
