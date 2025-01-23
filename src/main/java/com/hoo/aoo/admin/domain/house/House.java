package com.hoo.aoo.admin.domain.house;

import com.hoo.aoo.admin.domain.Area;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.RoomNameNotFoundException;
import com.hoo.aoo.admin.domain.file.File;
import com.hoo.aoo.admin.domain.file.FileType;
import com.hoo.aoo.admin.domain.house.room.Room;
import com.hoo.aoo.common.domain.BaseTime;
import com.hoo.aoo.admin.domain.file.FileId;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
public class House {

    private final HouseId houseId;
    private final Detail detail;
    private final Area area;
    private final BaseTime baseTime;
    private final List<Room> rooms;
    private final File basicImageFile;
    private final File borderImageFile;

    private House(HouseId houseId, Detail detail, Area area, BaseTime baseTime, List<Room> rooms, File basicImageFile, File borderImageFile) {
        this.houseId = houseId;
        this.area = area;
        this.detail = detail;
        this.baseTime = baseTime;
        this.rooms = rooms;
        this.basicImageFile = basicImageFile;
        this.borderImageFile = borderImageFile;
    }

    public static House create(HouseId houseId, Detail detail, Float width, Float height, Long defaultImageFileId, Long borderImageFileId, List<Room> rooms) throws AreaLimitExceededException {

        Area area = new Area(width, height);
        File defaultImageFile = new File(new FileId(defaultImageFileId), FileType.IMAGE);
        File borderImageFile = new File(new FileId(borderImageFileId), FileType.IMAGE);

        return new House(houseId, detail, area, null, rooms, defaultImageFile, borderImageFile);
    }

    public static House load(Long houseId, String title, String author, String description, Float width, Float height, ZonedDateTime createdTime, ZonedDateTime updatedTime, Long defaultImageFileId, Long borderImageFileId, List<Room> rooms) throws AreaLimitExceededException {

        Detail detail = new Detail(title, author, description);
        BaseTime baseTime = new BaseTime(createdTime, updatedTime);

        Area area = new Area(width, height);
        File defaultImageFile = new File(new FileId(defaultImageFileId), FileType.IMAGE);
        File borderImageFile = new File(new FileId(borderImageFileId), FileType.IMAGE);

        return new House(new HouseId(houseId), detail, area, baseTime, rooms, borderImageFile, defaultImageFile);
    }

    public void updateInfo(String title, String author, String description) {
        detail.update(title, author, description);
    }


    public void updateRoomInfo(String originalName, String newName) throws RoomNameNotFoundException {

        for (Room room : rooms) {
            if (room.getId().getName().equals(originalName)) {
                room.getId().update(newName);
                return;
            }
        }

        throw new RoomNameNotFoundException(detail.getTitle(), originalName);
    }

}
