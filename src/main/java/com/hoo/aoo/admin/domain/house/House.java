package com.hoo.aoo.admin.domain.house;

import com.hoo.aoo.admin.domain.Area;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.HouseRelationshipException;
import com.hoo.aoo.admin.domain.exception.RoomNameDuplicatedException;
import com.hoo.aoo.admin.domain.exception.RoomNameNotFoundException;
import com.hoo.aoo.admin.domain.house.room.Room;
import com.hoo.aoo.admin.domain.house.room.RoomId;
import com.hoo.aoo.common.domain.BaseTime;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
public class House {

    private final HouseId id;
    private final Area area;
    private final BaseTime baseTime;
    private final List<Room> rooms;

    private House(HouseId id, Area area, BaseTime baseTime, List<Room> rooms) {
        this.area = area;
        this.id = id;
        this.baseTime = baseTime;
        this.rooms = rooms;
    }

    public static House create(HouseId houseId, Float width, Float height, List<Room> rooms) throws AreaLimitExceededException, RoomNameDuplicatedException, HouseRelationshipException {

        Area area = new Area(width, height);
        House house = new House(houseId, area, null, rooms);

        house.verifyRoom();

        return house;
    }

    public static House load(String title, String author, String description, Float width, Float height, ZonedDateTime createdTime, ZonedDateTime updatedTime, List<Room> rooms) throws AreaLimitExceededException {

        HouseId houseId = new HouseId(title, author, description);
        Area area = new Area(width, height);
        BaseTime baseTime = new BaseTime(createdTime, updatedTime);

        return new House(houseId, area, baseTime, rooms);
    }

    private void verifyRoom() throws RoomNameDuplicatedException, HouseRelationshipException {
        for (int i = 0; i < rooms.size(); i++) {

            RoomId roomId = rooms.get(i).getId();

            if (roomId.getHouseId() == null || !roomId.getHouseId().equals(this.id))
                throw new HouseRelationshipException(roomId, this.id);

            for (int j = i + 1; j < rooms.size(); j++) {

                RoomId anotherRoomId = rooms.get(j).getId();

                if (roomId.getName().equals(anotherRoomId.getName()))
                    throw new RoomNameDuplicatedException(id.getTitle(), roomId.getName());
            }
        }
    }

    public void updateInfo(String title, String author, String description) {
        id.update(title, author, description);
    }


    public void updateRoomInfo(String originalName, String newName) throws RoomNameDuplicatedException, RoomNameNotFoundException {

        for (Room room : rooms) {
            if (room.getId().getName().equals(newName))
                throw new RoomNameDuplicatedException(id.getTitle(), newName);
        }

        for (Room room : rooms) {
            if (room.getId().getName().equals(originalName)) {
                room.getId().update(newName);
                return;
            }
        }

        throw new RoomNameNotFoundException(id.getTitle(), originalName);
    }

    public Room getRoom(String name) throws RoomNameNotFoundException {
        for (Room room : rooms) {
            if (room.getId().getName().equals(name))
                return room;
        }

        throw new RoomNameNotFoundException(id.getTitle(), name);
    }
}
