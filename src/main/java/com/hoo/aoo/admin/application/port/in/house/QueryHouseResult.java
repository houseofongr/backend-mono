package com.hoo.aoo.admin.application.port.in.house;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.common.adapter.in.web.DateTimeFormatters;

import java.util.List;

public record QueryHouseResult(
        House house,
        List<Room> rooms
) {
    public static QueryHouseResult of(HouseJpaEntity entity) {
        House house = new House(
                entity.getId(),
                entity.getTitle(),
                entity.getAuthor(),
                entity.getDescription(),
                DateTimeFormatters.ENGLISH_DATE.getFormatter().format(entity.getCreatedTime()),
                DateTimeFormatters.ENGLISH_DATE.getFormatter().format(entity.getUpdatedTime()),
                entity.getWidth(),
                entity.getHeight(),
                entity.getBorderImageFileId()
        );

        List<Room> list = entity.getRooms().stream().map(roomJpaEntity ->
                new Room(
                        roomJpaEntity.getId(),
                        roomJpaEntity.getName(),
                        roomJpaEntity.getX(),
                        roomJpaEntity.getY(),
                        roomJpaEntity.getZ(),
                        roomJpaEntity.getWidth(),
                        roomJpaEntity.getHeight(),
                        roomJpaEntity.getImageFileId()))
                .toList();

        return new QueryHouseResult(house,list);
    }

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
