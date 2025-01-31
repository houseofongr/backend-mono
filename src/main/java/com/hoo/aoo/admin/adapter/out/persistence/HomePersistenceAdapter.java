package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.aar.adapter.out.persistence.repository.UserJpaRepository;
import com.hoo.aoo.admin.adapter.out.persistence.entity.HomeJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.mapper.HomeMapper;
import com.hoo.aoo.admin.adapter.out.persistence.repository.HomeJpaRepository;
import com.hoo.aoo.admin.adapter.out.persistence.repository.HouseJpaRepository;
import com.hoo.aoo.admin.application.port.in.home.CreateHomeCommand;
import com.hoo.aoo.admin.application.port.in.home.CreateHomeResult;
import com.hoo.aoo.admin.application.port.in.home.QueryHomeResult;
import com.hoo.aoo.admin.application.port.in.home.QueryUserHomesResult;
import com.hoo.aoo.admin.application.port.out.home.DeleteHomePort;
import com.hoo.aoo.admin.application.port.out.home.FindHomePort;
import com.hoo.aoo.admin.application.port.out.home.SaveHomePort;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import com.hoo.aoo.admin.domain.home.Home;
import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HomePersistenceAdapter implements SaveHomePort, FindHomePort, DeleteHomePort {

    private final HouseJpaRepository houseJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final HomeJpaRepository homeJpaRepository;
    private final HomeMapper homeMapper;

    @Override
    public Long save(Home home) {
        UserJpaEntity userJpaEntity = userJpaRepository.findById(home.getUserId().getId()).orElseThrow();
        HouseJpaEntity houseJpaEntity = houseJpaRepository.findById(home.getHouseId().getId()).orElseThrow();

        if (homeJpaRepository.existsByHouseIdAndUserId(home.getHouseId().getId(), home.getUserId().getId()))
            throw new AdminException(AdminErrorCode.ALREADY_CREATED_HOME);

        HomeJpaEntity homeJpaEntity = HomeJpaEntity.create(home);
        homeJpaEntity.setRelationship(userJpaEntity, houseJpaEntity);

        homeJpaRepository.save(homeJpaEntity);

        return homeJpaEntity.getId();
    }

    @Override
    public boolean exist(Long homeId) {
        return homeJpaRepository.existsById(homeId);
    }

    @Override
    public boolean existByHouseId(Long houseId) {
        return homeJpaRepository.existsByHouseId(houseId);
    }

    @Override
    public Optional<Home> loadHome(Long id) {
        return homeJpaRepository.findById(id)
                .map(homeMapper::mapToDomainEntity);
    }

    @Override
    public List<Home> loadHomes(Long userId) {
        return homeJpaRepository.findAllByUserId(userId)
                .stream().map(homeMapper::mapToDomainEntity).toList();
    }

    @Override
    public void deleteHome(Long id) {
        if (!homeJpaRepository.existsById(id))
            throw new AdminException(AdminErrorCode.HOME_NOT_FOUND);

        homeJpaRepository.deleteById(id);
    }
}
