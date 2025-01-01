package com.hoo.aar.adapter.out.persistence.adapter;

import com.hoo.aar.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.hoo.aar.application.port.out.database.LoadSnsAccountPort;
import com.hoo.aar.application.port.out.database.SaveSnsAccountPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SnsAccountPersistenceAdapter implements LoadSnsAccountPort, SaveSnsAccountPort {

    @Override
    public Optional<SnsAccountJpaEntity> load(String snsId) {
        return null;
    }

    @Override
    public SnsAccountJpaEntity save(SnsAccountJpaEntity snsAccount) {
        return null;
    }
}
