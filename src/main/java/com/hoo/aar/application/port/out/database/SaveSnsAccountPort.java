package com.hoo.aar.application.port.out.database;

import com.hoo.aar.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.hoo.aar.domain.SnsAccount;

public interface SaveSnsAccountPort {
    SnsAccount save(SnsAccount snsAccount);
}
