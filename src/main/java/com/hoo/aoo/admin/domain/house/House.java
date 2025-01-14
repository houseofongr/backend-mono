package com.hoo.aoo.admin.domain.house;

import com.hoo.aoo.admin.domain.Area;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.HouseRelationshipException;
import com.hoo.aoo.admin.domain.exception.RoomDuplicatedException;
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

    public static House create(HouseId houseId, Integer width, Integer height, Long basicImageId, Long borderImageId, List<RoomId> rooms) throws AreaLimitExceededException, RoomDuplicatedException, HouseRelationshipException {

        Area area = new Area(width, height);
        HouseImages houseImages = new HouseImages(basicImageId, borderImageId);

        House house = new House(houseId, area, houseImages, rooms);

        house.verifyRoom(rooms);

        return house;
    }

    private void verifyRoom(List<RoomId> rooms) throws RoomDuplicatedException, HouseRelationshipException {
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getHouseId() == null || !rooms.get(i).getHouseId().equals(this.id))
                throw new HouseRelationshipException(rooms.get(i), this.id);

            for (int j = i + 1; j < rooms.size(); j++) {
                if (rooms.get(i).getName().equals(rooms.get(j).getName()))
                    throw new RoomDuplicatedException(rooms.get(i));
            }
        }
    }
}
