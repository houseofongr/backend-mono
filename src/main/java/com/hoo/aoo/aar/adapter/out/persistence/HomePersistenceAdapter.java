package com.hoo.aoo.aar.adapter.out.persistence;

import com.hoo.aoo.aar.adapter.out.persistence.mapper.HomeMapper;
import com.hoo.aoo.aar.application.port.in.home.QueryUserHomesResult;
import com.hoo.aoo.aar.application.port.out.home.QueryHomePort;
import com.hoo.aoo.common.adapter.out.persistence.repository.HomeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("AARHomePersistenceAdapter")
@RequiredArgsConstructor
public class HomePersistenceAdapter implements QueryHomePort {

    private final HomeJpaRepository homeJpaRepository;
    private final HomeMapper homeMapper;

    @Override
    public QueryUserHomesResult queryUserHome(Long userId) {
        return homeMapper.mapToQueryHomeRooms(homeJpaRepository.findAllByUserId(userId));
    }
}
