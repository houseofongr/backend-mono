package com.hoo.aoo.admin.application.port.in;

import com.hoo.aoo.admin.adapter.out.persistence.entity.RoomJpaEntity;

public record QueryRoomResult(
        Room room
) {
    public static QueryRoomResult of(RoomJpaEntity entity) {
        return new QueryRoomResult(
                new Room(entity.getName(), entity.getWidth(), entity.getHeight(), entity.getImageFileId())
        );
    }

    public record Room(
            String name,
            Float width,
            Float height,
            Long imageId
    ) {

    }
}
