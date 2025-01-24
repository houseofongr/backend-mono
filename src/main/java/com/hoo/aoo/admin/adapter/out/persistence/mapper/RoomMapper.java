package com.hoo.aoo.admin.adapter.out.persistence.mapper;

import com.hoo.aoo.admin.adapter.out.persistence.entity.RoomJpaEntity;
import com.hoo.aoo.admin.application.port.in.room.QueryRoomResult;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.room.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomMapper {

    private final ItemMapper itemMapper;

    public RoomJpaEntity mapToNewJpaEntity(Room room) {
        return new RoomJpaEntity(null,
                room.getRoomName().getName(),
                room.getAxis().getX(),
                room.getAxis().getY(),
                room.getAxis().getZ(),
                room.getArea().getWidth(),
                room.getArea().getHeight(),
                room.getImageFile().getFileId().getId(),
                null,
                null, null, null
        );
    }

    public QueryRoomResult mapToQueryRoomResult(RoomJpaEntity roomJpaEntity) {
        return QueryRoomResult.of(
                roomJpaEntity,
                roomJpaEntity.getItems().stream().map(itemMapper::mapToItemData).toList()
        );
    }

    public Room mapToDomainEntity(RoomJpaEntity roomJpaEntity) throws AreaLimitExceededException, AxisLimitExceededException {
        return Room.load(
                roomJpaEntity.getId(),
                roomJpaEntity.getName(),
                roomJpaEntity.getX(),
                roomJpaEntity.getY(),
                roomJpaEntity.getZ(),
                roomJpaEntity.getWidth(),
                roomJpaEntity.getHeight(),
                roomJpaEntity.getImageFileId());
    }
}
