package com.hoo.aoo.admin.adapter.out.persistence.repository;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HomeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeJpaRepository extends JpaRepository<HomeJpaEntity, Long>, HomeQueryDslRepository {
    List<HomeJpaEntity> findAllByUserId(Long id);
}
