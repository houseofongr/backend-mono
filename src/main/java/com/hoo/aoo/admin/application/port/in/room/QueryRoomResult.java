package com.hoo.aoo.admin.application.port.in.room;

import com.hoo.aoo.admin.adapter.out.persistence.entity.RoomJpaEntity;
import com.hoo.aoo.admin.application.port.in.item.ItemData;

import java.util.List;

public record QueryRoomResult(
        RoomData room,
        List<ItemData> items
) {
    public static QueryRoomResult of(RoomJpaEntity entity, List<ItemData> itemData) {
        return new QueryRoomResult(
                new RoomData(entity.getName(),
                        entity.getWidth(),
                        entity.getHeight(),
                        entity.getImageFileId()),
                itemData
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
