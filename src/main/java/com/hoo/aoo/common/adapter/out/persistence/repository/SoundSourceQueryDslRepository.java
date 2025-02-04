package com.hoo.aoo.common.adapter.out.persistence.repository;

public interface SoundSourceQueryDslRepository {
    boolean existsByUserIdAndId(Long userId, Long soundSourceId);
}
