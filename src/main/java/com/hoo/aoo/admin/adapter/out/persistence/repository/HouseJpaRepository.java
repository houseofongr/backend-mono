package com.hoo.aoo.admin.adapter.out.persistence.repository;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseJpaRepository extends JpaRepository<HouseJpaEntity, Long> {
}
