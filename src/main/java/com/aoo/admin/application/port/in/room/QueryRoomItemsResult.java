package com.aoo.admin.application.port.in.room;

import com.aoo.admin.application.port.in.item.ItemData;
import com.aoo.admin.domain.house.room.Room;
import com.aoo.admin.domain.item.Item;

import java.util.List;

public record QueryRoomItemsResult(
        RoomData room,
        List<ItemData> items
) {

    public static QueryRoomItemsResult of(Room room, List<Item> items) {
        return new QueryRoomItemsResult(
                new RoomData(
                        room.getRoomDetail().getName(),
                        room.getArea().getWidth(),
                        room.getArea().getHeight(),
                        room.getImageFile().getFileId().getId()
                ),
                items.stream().map(ItemData::of).toList()
        );
    }

    public record RoomData(
            String name,
            Float width,
            Float height,
            Long imageId
    ) {

    }

}
