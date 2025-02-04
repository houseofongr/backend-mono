package com.hoo.aoo.admin.adapter.out.persistence.mapper;

import com.hoo.aoo.common.adapter.out.persistence.entity.HomeJpaEntity;
import com.hoo.aoo.admin.domain.home.Home;
import org.springframework.stereotype.Component;

@Component
public class HomeMapper {

    public Home mapToDomainEntity(HomeJpaEntity homeJpaEntity) {
        return Home.load(homeJpaEntity.getId(), homeJpaEntity.getHouse().getId(), homeJpaEntity.getUser().getId(), homeJpaEntity.getName(), homeJpaEntity.getCreatedTime(), homeJpaEntity.getUpdatedTime());
    }

}
