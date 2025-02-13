package com.hoo.aoo.aar.adapter.out.persistence;

import com.hoo.aoo.aar.adapter.out.persistence.mapper.UserMapper;
import com.hoo.aoo.aar.adapter.out.persistence.repository.SnsAccountJpaRepository;
import com.hoo.aoo.aar.adapter.out.persistence.repository.UserJpaRepository;
import com.hoo.aoo.aar.application.port.in.user.QueryMyInfoResult;
import com.hoo.aoo.aar.application.port.out.persistence.user.QueryUserPort;
import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("AARUserPersistenceAdapter")
@AllArgsConstructor
public class UserPersistenceAdapter implements QueryUserPort {

    private final UserJpaRepository userJpaRepository;
    private final SnsAccountJpaRepository snsAccountJpaRepository;
    private final UserMapper userMapper;

    @Override
    public QueryMyInfoResult queryMyInfo(Long userId) {
        UserJpaEntity userJpaEntity = userJpaRepository.findById(userId).orElseThrow();
        Integer homeCount = userJpaRepository.countHomeCountById(userId);
        Integer soundSourceCount = userJpaRepository.countActiveSoundSourceCountById(userId);
        return userMapper.mapToQueryMyInfoResult(userJpaEntity, homeCount, soundSourceCount);
    }

}
