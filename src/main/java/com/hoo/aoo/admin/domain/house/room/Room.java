package com.hoo.aoo.admin.domain.house.room;

import com.hoo.aoo.admin.domain.Area;
import com.hoo.aoo.admin.domain.Axis;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.file.File;
import com.hoo.aoo.admin.domain.file.FileId;
import com.hoo.aoo.admin.domain.file.FileType;
import lombok.Getter;

@Getter
public class Room {

    private final RoomId roomId;
    private final Detail detail;
    private final Area area;
    private final Axis axis;
    private final File imageFile;

    private Room(RoomId roomId, Detail detail, Area area, Axis axis, File imageFile) {
        this.roomId = roomId;
        this.area = area;
        this.detail = detail;
        this.axis = axis;
        this.imageFile = imageFile;
    }

    public static Room create(Long id, String name, Float x, Float y, Float z, Float width, Float height, Long imageFileId) throws AxisLimitExceededException, AreaLimitExceededException {

        RoomId roomId = new RoomId(id);
        Detail detail = new Detail(name);
        Axis axis = new Axis(x, y, z);
        Area area = new Area(width, height);
        File imageFile = new File(new FileId(imageFileId), FileType.IMAGE);

        return new Room(roomId, detail, area, axis, imageFile);
    }

    public static Room load(Long id, String name, Float x, Float y, Float z, Float width, Float height, Long imageFileId) throws AreaLimitExceededException, AxisLimitExceededException {

        RoomId roomId = new RoomId(id);
        Detail detail = new Detail(name);
        Axis axis = new Axis(x, y, z);
        Area area = new Area(width, height);
        File imageFile = new File(new FileId(imageFileId), FileType.IMAGE);

        return new Room(roomId, detail, area, axis, imageFile);
    }

    public void updateInfo(String name) {
        detail.update(name);
    }
}
