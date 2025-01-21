package com.hoo.aoo.admin.adapter.out.persistence.repository;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HomeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeJpaRepository extends JpaRepository<HomeJpaEntity, Long>, HomeQueryDslRepository {
}
