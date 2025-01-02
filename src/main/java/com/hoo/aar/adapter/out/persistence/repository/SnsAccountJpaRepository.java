package com.hoo.aar.adapter.out.persistence.repository;

import com.hoo.aar.adapter.out.persistence.entity.SnsAccountJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SnsAccountJpaRepository extends JpaRepository<SnsAccountJpaEntity, Long> {

    @Query("select s from SnsAccountJpaEntity s left join fetch s.userEntity")
    Optional<SnsAccountJpaEntity> findBySnsIdWithUserEntity(String snsId);
}
