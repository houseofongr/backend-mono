package com.aoo.common.adapter.out.persistence.repository;

import com.aoo.common.adapter.out.persistence.entity.SpaceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceJpaRepository extends JpaRepository<SpaceJpaEntity, Long> {
}
