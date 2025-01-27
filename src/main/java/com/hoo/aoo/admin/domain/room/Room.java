package com.hoo.aoo.admin.domain.room;

import com.hoo.aoo.admin.domain.Area;
import com.hoo.aoo.admin.domain.Axis;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.file.File;
import com.hoo.aoo.admin.domain.file.FileId;
import com.hoo.aoo.admin.domain.file.FileType;
import com.hoo.aoo.admin.domain.item.Item;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Room {

    private final RoomId roomId;
    private final RoomDetail roomDetail;
    private final Area area;
    private final Axis axis;
    private final File imageFile;
    private final List<Item> items;

    private Room(RoomId roomId, RoomDetail roomDetail, Area area, Axis axis, File imageFile, List<Item> items) {
        this.roomId = roomId;
        this.area = area;
        this.roomDetail = roomDetail;
        this.axis = axis;
        this.imageFile = imageFile;
        this.items = items;
    }

    public static Room create(Long id, String name, Float x, Float y, Float z, Float width, Float height, Long imageFileId) throws AxisLimitExceededException, AreaLimitExceededException {

        File imageFile = new File(new FileId(imageFileId), FileType.IMAGE);

        return new Room(
                new RoomId(id),
                new RoomDetail(name),
                new Area(width, height),
                new Axis(x, y, z),
                imageFile,
                new ArrayList<>());
    }

    public static Room load(Long id, String name, Float x, Float y, Float z, Float width, Float height, Long imageFileId, List<Item> items) throws AreaLimitExceededException, AxisLimitExceededException {

        File imageFile = new File(new FileId(imageFileId), FileType.IMAGE);

        return new Room(
                new RoomId(id),
                new RoomDetail(name),
                new Area(width, height),
                new Axis(x, y, z),
                imageFile,
                items);
    }

    public void updateDetail(String name) {
        roomDetail.update(name);
    }
}
