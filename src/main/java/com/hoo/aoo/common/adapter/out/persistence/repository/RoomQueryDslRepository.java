package com.hoo.aoo.common.adapter.out.persistence.repository;

import com.hoo.aoo.common.adapter.out.persistence.entity.RoomJpaEntity;

import java.util.Optional;

public interface RoomQueryDslRepository {
    Optional<RoomJpaEntity> findByIdAndHomeIdWithItem(Long homeId, Long roomId);
}
