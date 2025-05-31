package com.aoo.admin.domain.house.room;

import com.aoo.admin.domain.Area;
import com.aoo.admin.domain.Axis;
import com.aoo.admin.domain.file.File;
import com.aoo.admin.domain.file.FileId;
import com.aoo.admin.domain.file.FileType;
import lombok.Getter;

@Getter
public class Room {

    private final RoomId roomId;
    private final RoomDetail roomDetail;
    private final Area area;
    private final Axis axis;
    private final File imageFile;

    private Room(RoomId roomId, RoomDetail roomDetail, Area area, Axis axis, File imageFile) {
        this.roomId = roomId;
        this.area = area;
        this.roomDetail = roomDetail;
        this.axis = axis;
        this.imageFile = imageFile;
    }

    public static Room create(Long id, String name, Float x, Float y, Float z, Float width, Float height, Long imageFileId) {

        File imageFile = new File(new FileId(imageFileId), FileType.IMAGE);

        return new Room(
                new RoomId(id),
                new RoomDetail(name),
                new Area(width, height),
                new Axis(x, y, z),
                imageFile);
    }

    public static Room load(Long id, String name, Float x, Float y, Float z, Float width, Float height, Long imageFileId) {

        File imageFile = new File(new FileId(imageFileId), FileType.IMAGE);

        return new Room(
                new RoomId(id),
                new RoomDetail(name),
                new Area(width, height),
                new Axis(x, y, z),
                imageFile);
    }

    public void updateDetail(String name) {
        roomDetail.update(name);
    }
}
