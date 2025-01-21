package com.hoo.aoo.admin.application.port.in.house;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.common.adapter.in.web.DateTimeFormatters;

import java.util.List;

public record QueryHouseResult(
        House house,
        List<Room> rooms
) {

    public record House(
            Long houseId,
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
