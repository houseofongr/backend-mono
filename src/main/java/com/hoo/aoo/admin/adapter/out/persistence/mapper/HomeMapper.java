package com.hoo.aoo.admin.adapter.out.persistence.mapper;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HomeJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.entity.RoomJpaEntity;
import com.hoo.aoo.admin.application.port.in.home.QueryHomeResult;
import com.hoo.aoo.admin.application.port.in.home.QueryUserHomesResult;
import com.hoo.aoo.common.adapter.in.web.DateTimeFormatters;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HomeMapper {
    public QueryHomeResult mapToQueryHomeResult(HomeJpaEntity homeJpaEntity) {
        return QueryHomeResult.of(homeJpaEntity, mapToQueryResult(homeJpaEntity.getHouse().getRooms()));
    }

    public QueryUserHomesResult mapToQueryUserHomesResult(List<HomeJpaEntity> homeJpaEntities) {
        return new QueryUserHomesResult(homeJpaEntities.stream()
                .map(QueryUserHomesResult.HomeInfo::of).toList());
    }

    private List<QueryHomeResult.RoomInfo> mapToQueryResult(List<RoomJpaEntity> roomJpaEntities) {
        return roomJpaEntities.stream().map(QueryHomeResult.RoomInfo::of).toList();
    }
}
