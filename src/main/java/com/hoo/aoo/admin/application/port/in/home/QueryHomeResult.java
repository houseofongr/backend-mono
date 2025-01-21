package com.hoo.aoo.admin.application.port.in.home;

import java.util.List;

public record QueryHomeResult(
        Long homeId,
        String createdDate,
        String updatedDate,
        HouseInfo house,
        List<RoomInfo> rooms
) {

    public record HouseInfo(
            Float width,
            Float height,
            Long borderImageId
    ) {

    }

    public record RoomInfo(
            Long roomId,
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
