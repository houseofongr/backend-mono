package com.hoo.aoo.admin.adapter.out.persistence.mapper;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HomeJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.entity.RoomJpaEntity;
import com.hoo.aoo.admin.application.port.in.home.QueryHomeResult;
import com.hoo.aoo.admin.application.port.in.home.QueryUserHomesResult;
import com.hoo.aoo.admin.domain.home.Home;
import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HomeMapper {

    public Home mapToDomainEntity(HomeJpaEntity homeJpaEntity) {
        return Home.load(homeJpaEntity.getId(), homeJpaEntity.getHouse().getId(), homeJpaEntity.getUser().getId(), homeJpaEntity.getName(), homeJpaEntity.getCreatedTime(), homeJpaEntity.getUpdatedTime());
    }

}
