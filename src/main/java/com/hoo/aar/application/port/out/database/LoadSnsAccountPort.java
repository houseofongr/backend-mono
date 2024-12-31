package com.hoo.aar.application.port.out.database;

import com.hoo.aar.adapter.out.persistence.entity.SnsAccountJpaEntity;

import java.util.Optional;

public interface LoadSnsAccountPort {
    Optional<SnsAccountJpaEntity> load(String snsId);
}
