package com.hoo.aoo.admin.domain.house;

import com.hoo.aoo.admin.domain.Area;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.HouseRelationshipException;
import com.hoo.aoo.admin.domain.exception.RoomNameNotFoundException;
import com.hoo.aoo.admin.domain.file.File;
import com.hoo.aoo.admin.domain.file.FileType;
import com.hoo.aoo.admin.domain.house.room.Room;
import com.hoo.aoo.admin.domain.house.room.RoomId;
import com.hoo.aoo.common.domain.BaseTime;
import com.hoo.aoo.admin.domain.file.FileId;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
public class House {

    private final HouseId id;
    private final Area area;
    private final BaseTime baseTime;
    private final List<Room> rooms;
    private final File defaultImageFile;
    private final File borderImageFile;

    private House(HouseId id, Area area, BaseTime baseTime, List<Room> rooms, File defaultImageFile, File borderImageFile) {
        this.area = area;
        this.id = id;
        this.baseTime = baseTime;
        this.rooms = rooms;
        this.defaultImageFile = defaultImageFile;
        this.borderImageFile = borderImageFile;
    }

    public static House create(HouseId houseId, Float width, Float height, Long defaultImageFileId, Long borderImageFileId, List<Room> rooms) throws AreaLimitExceededException, HouseRelationshipException {

        Area area = new Area(width, height);
        File defaultImageFile = new File(new FileId(defaultImageFileId), FileType.IMAGE);
        File borderImageFile = new File(new FileId(borderImageFileId), FileType.IMAGE);

        House house = new House(houseId, area, null, rooms, defaultImageFile, borderImageFile);

        house.verifyRoom();

        return house;
    }

    public static House load(String title, String author, String description, Float width, Float height, ZonedDateTime createdTime, ZonedDateTime updatedTime, Long defaultImageFileId, Long borderImageFileId, List<Room> rooms) throws AreaLimitExceededException, HouseRelationshipException {

        HouseId houseId = new HouseId(title, author, description);
        BaseTime baseTime = new BaseTime(createdTime, updatedTime);

        Area area = new Area(width, height);
        File defaultImageFile = new File(new FileId(defaultImageFileId), FileType.IMAGE);
        File borderImageFile = new File(new FileId(borderImageFileId), FileType.IMAGE);

        House house = new House(houseId, area, baseTime, rooms, borderImageFile, defaultImageFile);

        house.verifyRoom();

        return house;
    }

    private void verifyRoom() throws HouseRelationshipException {
        for (Room room : rooms) {

            RoomId roomId = room.getId();

            if (roomId.getHouseId() == null || !roomId.getHouseId().equals(this.id))
                throw new HouseRelationshipException(roomId, this.id);
        }
    }

    public void updateInfo(String title, String author, String description) {
        id.update(title, author, description);
    }


    public void updateRoomInfo(String originalName, String newName) throws RoomNameNotFoundException {

        for (Room room : rooms) {
            if (room.getId().getName().equals(originalName)) {
                room.getId().update(newName);
                return;
            }
        }

        throw new RoomNameNotFoundException(id.getTitle(), originalName);
    }

}
