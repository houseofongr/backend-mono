package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import com.hoo.aoo.aar.adapter.out.persistence.repository.UserJpaRepository;
import com.hoo.aoo.admin.adapter.out.persistence.entity.HomeJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.repository.HomeJpaRepository;
import com.hoo.aoo.admin.adapter.out.persistence.repository.HouseJpaRepository;
import com.hoo.aoo.admin.application.port.in.home.CreateHomeCommand;
import com.hoo.aoo.admin.application.port.in.home.CreateHomeResult;
import com.hoo.aoo.admin.application.port.out.home.SaveHomePort;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HomePersistenceAdapter implements SaveHomePort {

    private final HouseJpaRepository houseJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final HomeJpaRepository homeJpaRepository;

    @Override
    public CreateHomeResult save(CreateHomeCommand command) {
        HouseJpaEntity houseJpaEntity = houseJpaRepository.findById(command.houseId())
                .orElseThrow(() -> new AdminException(AdminErrorCode.HOUSE_NOT_FOUND));

        UserJpaEntity userJpaEntity = userJpaRepository.findById(command.userId())
                .orElseThrow(() -> new AdminException(AdminErrorCode.USER_NOT_FOUND));

        HomeJpaEntity homeJpaEntity = HomeJpaEntity.create(houseJpaEntity, userJpaEntity);

        homeJpaRepository.save(homeJpaEntity);

        return new CreateHomeResult(homeJpaEntity.getId(), null);
    }
}
