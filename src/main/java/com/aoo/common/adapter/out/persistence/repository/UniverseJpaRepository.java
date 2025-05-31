package com.aoo.common.adapter.out.persistence.repository;

import com.aoo.common.adapter.out.persistence.entity.UniverseJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniverseJpaRepository extends JpaRepository<UniverseJpaEntity, Long>, UniverseQueryDslRepository {
}
