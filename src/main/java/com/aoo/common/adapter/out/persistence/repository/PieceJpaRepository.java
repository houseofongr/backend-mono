package com.aoo.common.adapter.out.persistence.repository;

import com.aoo.common.adapter.out.persistence.entity.PieceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PieceJpaRepository extends JpaRepository<PieceJpaEntity, Long>, PieceQueryDslRepository {
}
