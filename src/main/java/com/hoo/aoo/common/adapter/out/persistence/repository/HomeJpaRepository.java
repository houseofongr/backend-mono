package com.hoo.aoo.common.adapter.out.persistence.repository;

import com.hoo.aoo.common.adapter.out.persistence.entity.HomeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeJpaRepository extends JpaRepository<HomeJpaEntity, Long>, HomeQueryDslRepository {
    List<HomeJpaEntity> findAllByUserId(Long id);

    boolean existsByHouseIdAndUserId(Long houseId, Long userId);

    boolean existsByHouseId(Long houseId);
}
