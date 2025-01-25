package com.hoo.aoo.admin.adapter.out.persistence.mapper;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.entity.RoomJpaEntity;
import com.hoo.aoo.admin.application.port.in.house.QueryHouseResult;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.room.Room;
import com.hoo.aoo.common.adapter.in.web.DateTimeFormatters;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HouseMapper {

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
            Room loadedRoom = Room.load(
                    roomJpaEntity.getId(),
                    roomJpaEntity.getName(),
                    roomJpaEntity.getX(),
                    roomJpaEntity.getY(),
                    roomJpaEntity.getZ(),
                    roomJpaEntity.getWidth(),
                    roomJpaEntity.getHeight(),
                    roomJpaEntity.getImageFileId());

            house.getRooms().add(loadedRoom);
        }

        return house;
    }

    public QueryHouseResult mapToQueryHouseResult(HouseJpaEntity entity) {
        QueryHouseResult.House house = new QueryHouseResult.House(
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

        List<QueryHouseResult.Room> list = entity.getRooms().stream().map(roomJpaEntity ->
                        new QueryHouseResult.Room(
                                roomJpaEntity.getId(),
                                roomJpaEntity.getName(),
                                roomJpaEntity.getX(),
                                roomJpaEntity.getY(),
                                roomJpaEntity.getZ(),
                                roomJpaEntity.getWidth(),
                                roomJpaEntity.getHeight(),
                                roomJpaEntity.getImageFileId()))
                .toList();

        return new QueryHouseResult(house, list);
    }

}
