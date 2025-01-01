package com.hoo.aar.adapter.out.persistence.repository;

import com.hoo.aar.adapter.out.persistence.entity.SnsAccountJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SnsAccountJpaRepository extends JpaRepository<SnsAccountJpaEntity, Long> {
    Optional<SnsAccountJpaEntity> findBySnsId(String snsId);
}
