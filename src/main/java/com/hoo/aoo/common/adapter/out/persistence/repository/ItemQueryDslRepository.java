package com.hoo.aoo.common.adapter.out.persistence.repository;

public interface ItemQueryDslRepository {
    boolean existsByUserIdAndItemId(Long userId, Long itemId);
}
