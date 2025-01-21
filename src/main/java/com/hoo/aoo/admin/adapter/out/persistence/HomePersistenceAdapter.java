package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.admin.adapter.out.persistence.repository.HomeJpaRepository;
import com.hoo.aoo.admin.application.port.in.user.CreateHomeCommand;
import com.hoo.aoo.admin.application.port.in.user.CreateHomeResult;
import com.hoo.aoo.admin.application.port.out.user.SaveHomePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HomePersistenceAdapter implements SaveHomePort {

    private final HomeJpaRepository homeJpaRepository;

    @Override
    public CreateHomeResult save(CreateHomeCommand command) {
        return null;
    }
}
