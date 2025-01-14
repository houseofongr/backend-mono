package com.hoo.aoo.admin.domain.house;

import com.hoo.aoo.admin.domain.Area;
import com.hoo.aoo.common.domain.BaseTime;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.HouseRelationshipException;
import com.hoo.aoo.admin.domain.exception.RoomDuplicatedException;
import com.hoo.aoo.admin.domain.room.RoomId;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
public class House {

    private final HouseId id;
    private final Area area;
    private final HouseImages images;
    private final BaseTime baseTime;
    private final List<RoomId> rooms;

    private House(HouseId id, Area area, HouseImages images, BaseTime baseTime, List<RoomId> rooms) {
        this.area = area;
        this.id = id;
        this.images = images;
        this.baseTime = baseTime;
        this.rooms = rooms;
    }

    public static House create(HouseId houseId, Integer width, Integer height, Long basicImageId, Long borderImageId, List<RoomId> rooms) throws AreaLimitExceededException, RoomDuplicatedException, HouseRelationshipException {

        Area area = new Area(width, height);
        HouseImages houseImages = new HouseImages(basicImageId, borderImageId);

        House house = new House(houseId, area, houseImages, null, rooms);

        house.verifyRoom(rooms);

        return house;
    }

    public static House load(String title, String author, String description, Integer width, Integer height, Long basicImageId, Long borderImageId, ZonedDateTime createdTime, ZonedDateTime updatedTime, List<RoomId> rooms) throws AreaLimitExceededException {

        HouseId houseId = new HouseId(title, author, description);
        Area area = new Area(width, height);
        HouseImages houseImages = new HouseImages(basicImageId, borderImageId);
        BaseTime baseTime = new BaseTime(createdTime, updatedTime);

        return new House(houseId, area, houseImages, baseTime, rooms);
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
