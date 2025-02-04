package com.hoo.aoo.aar.adapter.out.persistence.mapper;

import com.hoo.aoo.aar.application.port.in.home.QueryHomeRoomsResult;
import com.hoo.aoo.aar.application.port.in.home.QueryUserHomesResult;
import com.hoo.aoo.common.adapter.out.persistence.entity.HomeJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.entity.RoomJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("AARHomeMapper")
public class HomeMapper {

    public QueryUserHomesResult mapToQueryUserHomes(List<HomeJpaEntity> homeJpaEntities) {
        return new QueryUserHomesResult(
                homeJpaEntities.stream().map(this::mapToHomeInfo).toList()
        );
    }

    private QueryUserHomesResult.HomeInfo mapToHomeInfo(HomeJpaEntity homeJpaEntity) {
        return new QueryUserHomesResult.HomeInfo(
                homeJpaEntity.getId(),
                homeJpaEntity.getHouse().getBasicImageFileId(),
                homeJpaEntity.getName());
    }

    public QueryHomeRoomsResult mapToQueryHomeRooms(HomeJpaEntity homeJpaEntity) {
        return new QueryHomeRoomsResult(
                homeJpaEntity.getName(),
                mapToHouseInfo(homeJpaEntity.getHouse()),
                homeJpaEntity.getHouse().getRooms().stream().map(this::mapToRoomInfo).toList()
        );
    }

    private QueryHomeRoomsResult.HouseInfo mapToHouseInfo(HouseJpaEntity house) {
        return new QueryHomeRoomsResult.HouseInfo(
                house.getWidth(),
                house.getHeight(),
                house.getBorderImageFileId()
        );
    }

    private QueryHomeRoomsResult.RoomInfo mapToRoomInfo(RoomJpaEntity roomJpaEntity) {
        return new QueryHomeRoomsResult.RoomInfo(
                roomJpaEntity.getId(),
                roomJpaEntity.getName(),
                roomJpaEntity.getX(),
                roomJpaEntity.getY(),
                roomJpaEntity.getZ(),
                roomJpaEntity.getWidth(),
                roomJpaEntity.getHeight(),
                roomJpaEntity.getImageFileId()
        );
    }
}
