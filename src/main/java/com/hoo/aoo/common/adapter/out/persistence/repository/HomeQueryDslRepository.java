package com.hoo.aoo.common.adapter.out.persistence.repository;

import com.hoo.aoo.common.adapter.out.persistence.entity.HomeJpaEntity;

import java.util.Optional;

public interface HomeQueryDslRepository {
    Optional<HomeJpaEntity> findByIdWithHouseAndRooms(Long id);
}
