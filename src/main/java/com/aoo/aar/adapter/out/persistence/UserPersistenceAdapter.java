package com.aoo.aar.adapter.out.persistence;

import com.aoo.aar.adapter.out.persistence.mapper.UserMapper;
import com.aoo.common.adapter.out.persistence.repository.UserJpaRepository;
import com.aoo.aar.application.port.in.user.SearchMyInfoResult;
import com.aoo.aar.application.port.out.persistence.user.SearchUserPort;
import com.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("AARUserPersistenceAdapter")
@RequiredArgsConstructor
public class UserPersistenceAdapter implements SearchUserPort {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Override
    public SearchMyInfoResult queryMyInfo(Long userId) {
        UserJpaEntity userJpaEntity = userJpaRepository.findById(userId).orElseThrow();
        Integer homeCount = userJpaRepository.countHomeCountById(userId);
        Integer soundSourceCount = userJpaRepository.countActiveSoundSourceCountById(userId);
        return userMapper.mapToQueryMyInfoResult(userJpaEntity, homeCount, soundSourceCount);
    }

    @Override
    public boolean existUserByNickname(String nickname) {
        return userJpaRepository.existsByNickname(nickname);
    }

}
