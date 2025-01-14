package com.hoo.aoo.admin.domain.house;

import com.hoo.aoo.admin.domain.Area;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.HouseRelationshipException;
import com.hoo.aoo.admin.domain.exception.RoomDuplicatedException;
import com.hoo.aoo.admin.domain.room.RoomId;
import com.hoo.aoo.common.domain.BaseTime;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
public class House {

    private final HouseId id;
    private final Area area;
    private final BaseTime baseTime;
    private final List<RoomId> rooms;

    private House(HouseId id, Area area, BaseTime baseTime, List<RoomId> rooms) {
        this.area = area;
        this.id = id;
        this.baseTime = baseTime;
        this.rooms = rooms;
    }

    public static House create(HouseId houseId, Integer width, Integer height, List<RoomId> rooms) throws AreaLimitExceededException, RoomDuplicatedException, HouseRelationshipException {

        Area area = new Area(width, height);

        House house = new House(houseId, area, null, rooms);

        house.verifyRoom(rooms);

        return house;
    }

    public static House load(String title, String author, String description, Integer width, Integer height, ZonedDateTime createdTime, ZonedDateTime updatedTime, List<RoomId> rooms) throws AreaLimitExceededException {

        HouseId houseId = new HouseId(title, author, description);
        Area area = new Area(width, height);
        BaseTime baseTime = new BaseTime(createdTime, updatedTime);

        return new House(houseId, area, baseTime, rooms);
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
