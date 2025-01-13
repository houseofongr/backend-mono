package com.hoo.aoo.admin.adapter.out.persistence.mapper;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.entity.RoomJpaEntity;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.room.Room;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HouseMapper {
    public HouseJpaEntity mapToNewEntity(House house, List<RoomJpaEntity> rooms) {
        HouseJpaEntity houseJpaEntity = new HouseJpaEntity(null,
                house.getId().getTitle(),
                house.getId().getAuthor(),
                house.getId().getDescription(),
                house.getArea().getWidth(),
                house.getArea().getHeight(),
                house.getImages().getBasicImageId(),
                house.getImages().getBorderImageId(),
                rooms
        );

        rooms.forEach(roomJpaEntity -> roomJpaEntity.setHouse(houseJpaEntity));

        return houseJpaEntity;
    }

    public RoomJpaEntity mapToNewEntity(Room room) {
        return new RoomJpaEntity(null,
                room.getId().getName(),
                room.getAxis().getX(),
                room.getAxis().getY(),
                room.getAxis().getZ(),
                room.getArea().getWidth(),
                room.getArea().getHeight(),
                room.getImage().getImageId(),
                null
        );
    }
}
