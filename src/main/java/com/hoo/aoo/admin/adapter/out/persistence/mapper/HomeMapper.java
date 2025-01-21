package com.hoo.aoo.admin.adapter.out.persistence.mapper;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HomeJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.entity.RoomJpaEntity;
import com.hoo.aoo.admin.application.port.in.home.QueryHomeResult;
import com.hoo.aoo.common.adapter.in.web.DateTimeFormatters;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HomeMapper {
    public QueryHomeResult mapToQueryResult(HomeJpaEntity homeJpaEntity) {
        return new QueryHomeResult(
                homeJpaEntity.getId(),
                homeJpaEntity.getName(),
                DateTimeFormatters.ENGLISH_DATE.getFormatter().format(homeJpaEntity.getCreatedTime()),
                DateTimeFormatters.ENGLISH_DATE.getFormatter().format(homeJpaEntity.getUpdatedTime()),
                mapToQueryResult(homeJpaEntity.getHouse()),
                mapToQueryResult(homeJpaEntity.getHouse().getRooms())
        );
    }

    private QueryHomeResult.HouseInfo mapToQueryResult(HouseJpaEntity houseJpaEntity) {
        return new QueryHomeResult.HouseInfo(
                houseJpaEntity.getWidth(),
                houseJpaEntity.getHeight(),
                houseJpaEntity.getBorderImageFileId()
        );
    }

    private List<QueryHomeResult.RoomInfo> mapToQueryResult(List<RoomJpaEntity> roomJpaEntities) {
        return roomJpaEntities.stream().map(roomJpaEntity ->
                new QueryHomeResult.RoomInfo(roomJpaEntity.getId(),
                        roomJpaEntity.getName(),
                        roomJpaEntity.getX(),
                        roomJpaEntity.getY(),
                        roomJpaEntity.getZ(),
                        roomJpaEntity.getWidth(),
                        roomJpaEntity.getHeight(),
                        roomJpaEntity.getImageFileId())).toList();
    }
}
