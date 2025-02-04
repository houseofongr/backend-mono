package com.hoo.aoo.admin.adapter.out.persistence.mapper;

import com.hoo.aoo.common.adapter.out.persistence.entity.RoomJpaEntity;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.room.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomMapper {

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
