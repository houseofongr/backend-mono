package com.hoo.aoo.admin.application.port.out;

import com.hoo.aoo.admin.adapter.out.persistence.entity.RoomJpaEntity;

import java.util.Optional;

public interface FindRoomPort {
    Optional<RoomJpaEntity> findJpaEntity(Long id, String roomName);
}
