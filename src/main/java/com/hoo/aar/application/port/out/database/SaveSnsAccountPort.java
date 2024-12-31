package com.hoo.aar.application.port.out.database;

import com.hoo.aar.adapter.out.persistence.entity.SnsAccountJpaEntity;

public interface SaveSnsAccountPort {
    SnsAccountJpaEntity save(SnsAccountJpaEntity snsAccount);
}
