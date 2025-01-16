package com.hoo.aoo.admin.application.port.in;

public record LoadRoomResult(
        Room room
) {
    public record Room(
            String name,
            Float width,
            Float height,
            Long imageId
    ) {

    }
}
