package com.hoo.aoo.admin.domain.house.room;

import com.hoo.aoo.admin.domain.Area;
import com.hoo.aoo.admin.domain.Axis;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.file.File;
import com.hoo.aoo.admin.domain.file.FileId;
import com.hoo.aoo.admin.domain.file.FileType;
import com.hoo.aoo.admin.domain.house.HouseId;
import lombok.Getter;

@Getter
public class Room {

    private final RoomId id;
    private final Area area;
    private final Axis axis;
    private final File imageFile;

    private Room(RoomId id, Area area, Axis axis, File imageFile) {
        this.area = area;
        this.id = id;
        this.axis = axis;
        this.imageFile = imageFile;
    }

    public static Room create(HouseId houseId, String name, Float x, Float y, Float z, Float width, Float height, Long imageFileId) throws AxisLimitExceededException, AreaLimitExceededException {

        RoomId roomId = new RoomId(houseId, name);
        Axis axis = new Axis(x, y, z);
        Area area = new Area(width, height);
        File imageFile = new File(new FileId(imageFileId), FileType.IMAGE);

        return new Room(roomId, area, axis, imageFile);
    }

    public static Room load(HouseId houseId, String name, Float x, Float y, Float z, Float width, Float height, Long imageFileId) throws AreaLimitExceededException, AxisLimitExceededException {

        RoomId roomId = new RoomId(houseId, name);
        Axis axis = new Axis(x, y, z);
        Area area = new Area(width, height);
        File imageFile = new File(new FileId(imageFileId), FileType.IMAGE);

        return new Room(roomId, area, axis, imageFile);
    }

    public void updateInfo(String name) {
        id.update(name);
    }
}
