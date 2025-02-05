package com.hoo.aoo.common.adapter.out.persistence.repository;

import com.hoo.aoo.common.adapter.out.persistence.entity.SoundSourceJpaEntity;

import java.util.List;

public interface SoundSourceQueryDslRepository {
    boolean existsByUserIdAndId(Long userId, Long soundSourceId);
    List<SoundSourceJpaEntity> findAllByIdWithPathEntity(Long userId);
}
