package com.hoo.aoo.admin.adapter.out.persistence.mapper;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.entity.RoomJpaEntity;
import com.hoo.aoo.admin.application.port.in.house.QueryHouseResult;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.room.Room;
import com.hoo.aoo.common.adapter.in.web.DateTimeFormatters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HouseMapper {

    private final RoomMapper roomMapper;

    public HouseJpaEntity mapToNewJpaEntity(House house, List<RoomJpaEntity> roomJpaEntities) {

        HouseJpaEntity houseJpaEntity = new HouseJpaEntity(null,
                house.getHouseDetail().getTitle(),
                house.getHouseDetail().getAuthor(),
                house.getHouseDetail().getDescription(),
                house.getArea().getWidth(),
                house.getArea().getHeight(),
                house.getBasicImageFile().getFileId().getId(),
                house.getBorderImageFile().getFileId().getId(),
                roomJpaEntities
        );

        roomJpaEntities.forEach(roomJpaEntity -> roomJpaEntity.setHouse(houseJpaEntity));

        return houseJpaEntity;
    }

    public House mapToDomainEntity(HouseJpaEntity houseJpaEntity, List<RoomJpaEntity> roomJpaEntities) throws AreaLimitExceededException, AxisLimitExceededException {

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

        for (RoomJpaEntity roomJpaEntity : roomJpaEntities) {
            Room loadedRoom = roomMapper.mapToDomainEntity(roomJpaEntity);
            house.getRooms().add(loadedRoom);
        }

        return house;
    }
}
