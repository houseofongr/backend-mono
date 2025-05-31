package com.aoo.admin.adapter.out.persistence.mapper;

import com.aoo.admin.domain.exception.AreaLimitExceededException;
import com.aoo.admin.domain.exception.AxisLimitExceededException;
import com.aoo.admin.domain.house.House;
import com.aoo.common.adapter.out.persistence.entity.HouseJpaEntity;
import com.aoo.common.adapter.out.persistence.entity.RoomJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class HouseMapper {

    private final RoomMapper roomMapper;

    public House mapToDomainEntity(HouseJpaEntity houseJpaEntity) throws AreaLimitExceededException, AxisLimitExceededException {

        House house = House.load(
                houseJpaEntity.getId(),
                houseJpaEntity.getTitle(),
                houseJpaEntity.getAuthor(),
                houseJpaEntity.getDescription(),
                houseJpaEntity.getWidth(),
                houseJpaEntity.getHeight(),
                houseJpaEntity.getCreatedTime(),
                houseJpaEntity.getUpdatedTime(),
                houseJpaEntity.getBasicImageFileId(),
                houseJpaEntity.getBorderImageFileId(),
                new ArrayList<>()
        );

        for (RoomJpaEntity roomJpaEntity : houseJpaEntity.getRooms())
            house.getRooms().add(roomMapper.mapToDomainEntity(roomJpaEntity));

        return house;
    }
}
