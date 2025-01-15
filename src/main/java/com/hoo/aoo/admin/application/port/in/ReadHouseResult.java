package com.hoo.aoo.admin.application.port.in;

import java.util.List;

public record ReadHouseResult(
        House house,
        List<Room> rooms
) {
    public record House(
            String title,
            String author,
            String description,
            String createdDate,
            String updatedDate,
            Float width,
            Float height,
            Long borderImageId
    ) {

    }

    public record Room(
            String name,
            Float x,
            Float y,
            Float z,
            Float width,
            Float height,
            Long imageId
    ) {

    }
}
