package com.hoo.aoo.aar.adapter.out.persistence.mapper;

import com.hoo.aoo.aar.application.port.in.home.QueryUserHomesResult;
import com.hoo.aoo.common.adapter.out.persistence.entity.HomeJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("AARHomeMapper")
public class HomeMapper {

    public QueryUserHomesResult mapToQueryHomeRooms(List<HomeJpaEntity> homeJpaEntities) {
        return new QueryUserHomesResult(
                homeJpaEntities.stream().map(this::mapToHomeInfo).toList()
        );
    }

    private QueryUserHomesResult.HomeInfo mapToHomeInfo(HomeJpaEntity homeJpaEntity) {
        return new QueryUserHomesResult.HomeInfo(homeJpaEntity.getId(), homeJpaEntity.getName());
    }
}
