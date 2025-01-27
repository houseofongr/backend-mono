package com.hoo.aoo.admin.adapter.out.persistence.mapper;

import com.hoo.aoo.admin.adapter.out.persistence.entity.ItemJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.entity.RoomJpaEntity;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.room.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class RoomMapper {

    private final ItemMapper itemMapper;

    public RoomJpaEntity mapToNewJpaEntity(Room room) {
        return new RoomJpaEntity(null,
                room.getRoomDetail().getName(),
                room.getAxis().getX(),
                room.getAxis().getY(),
                room.getAxis().getZ(),
                room.getArea().getWidth(),
                room.getArea().getHeight(),
                room.getImageFile().getFileId().getId(),
                null,
                null
        );
    }

    public Room mapToDomainEntity(RoomJpaEntity roomJpaEntity) throws AreaLimitExceededException, AxisLimitExceededException {
        Room room = Room.load(
                roomJpaEntity.getId(),
                roomJpaEntity.getName(),
                roomJpaEntity.getX(),
                roomJpaEntity.getY(),
                roomJpaEntity.getZ(),
                roomJpaEntity.getWidth(),
                roomJpaEntity.getHeight(),
                roomJpaEntity.getImageFileId(),
                new ArrayList<>());

        for (ItemJpaEntity itemJpaEntity : roomJpaEntity.getItems())
            room.getItems().add(itemMapper.mapToDomainEntity(itemJpaEntity));

        return room;
    }
}
