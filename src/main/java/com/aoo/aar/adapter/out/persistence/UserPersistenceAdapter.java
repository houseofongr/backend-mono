package com.aoo.aar.adapter.out.persistence;

import com.aoo.aar.adapter.out.persistence.mapper.UserMapper;
import com.aoo.aar.adapter.out.persistence.repository.UserJpaRepository;
import com.aoo.aar.application.port.in.user.QueryMyInfoResult;
import com.aoo.aar.application.port.out.persistence.user.QueryUserPort;
import com.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component("AARUserPersistenceAdapter")
@AllArgsConstructor
public class UserPersistenceAdapter implements QueryUserPort {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Override
    public QueryMyInfoResult queryMyInfo(Long userId) {
        UserJpaEntity userJpaEntity = userJpaRepository.findById(userId).orElseThrow();
        Integer homeCount = userJpaRepository.countHomeCountById(userId);
        Integer soundSourceCount = userJpaRepository.countActiveSoundSourceCountById(userId);
        return userMapper.mapToQueryMyInfoResult(userJpaEntity, homeCount, soundSourceCount);
    }

}
