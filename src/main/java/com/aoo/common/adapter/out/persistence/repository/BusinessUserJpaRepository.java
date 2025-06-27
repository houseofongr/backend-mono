package com.aoo.common.adapter.out.persistence.repository;

import com.aoo.common.adapter.out.persistence.entity.BusinessUserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessUserJpaRepository extends JpaRepository<BusinessUserJpaEntity, Long> {
}
