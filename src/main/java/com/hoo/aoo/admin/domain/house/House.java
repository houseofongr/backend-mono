package com.hoo.aoo.admin.domain.house;

import com.hoo.aoo.admin.domain.Area;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.room.Room;
import com.hoo.aoo.admin.domain.room.RoomId;
import lombok.Getter;

import java.util.List;

@Getter
public class House {

    private final HouseId id;
    private final Area area;
    private final HouseImages images;
    private final List<RoomId> rooms;

    private House(HouseId id, Area area, HouseImages images, List<RoomId> rooms) {
        this.area = area;
        this.id = id;
        this.images = images;
        this.rooms = rooms;
    }

    public static House create(HouseId houseId, Integer width, Integer height, Long basicImageId, Long borderImageId, List<RoomId> rooms) throws AreaLimitExceededException {

        Area area = new Area(width, height);
        HouseImages houseImages = new HouseImages(basicImageId, borderImageId);

        return new House(houseId, area, houseImages, rooms);
    }
}
