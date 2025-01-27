package com.hoo.aoo.admin.adapter.out.persistence.repository;

import com.hoo.aoo.admin.adapter.out.persistence.entity.SoundSourceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoundSourceJpaRepository extends JpaRepository<SoundSourceJpaEntity, Long> {
}
