package com.hoo.aoo.admin.domain.room;

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
    private final RoomName roomName;
    private final Area area;
    private final Axis axis;
    private final File imageFile;

    private Room(RoomId roomId, RoomName roomName, Area area, Axis axis, File imageFile) {
        this.roomId = roomId;
        this.area = area;
        this.roomName = roomName;
        this.axis = axis;
        this.imageFile = imageFile;
    }

    public static Room create(Long id, String name, Float x, Float y, Float z, Float width, Float height, Long imageFileId) throws AxisLimitExceededException, AreaLimitExceededException {

        File imageFile = new File(new FileId(imageFileId), FileType.IMAGE);

        return new Room(
                new RoomId(id),
                new RoomName(name),
                new Area(width, height),
                new Axis(x, y, z),
                imageFile);
    }

    public static Room load(Long id, String name, Float x, Float y, Float z, Float width, Float height, Long imageFileId) throws AreaLimitExceededException, AxisLimitExceededException {

        File imageFile = new File(new FileId(imageFileId), FileType.IMAGE);

        return new Room(
                new RoomId(id),
                new RoomName(name),
                new Area(width, height),
                new Axis(x, y, z),
                imageFile);
    }

    public void updateInfo(String name) {
        roomName.update(name);
    }
}
