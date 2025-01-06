package com.hoo.aoo.aar.adapter.out.persistence.adapter;

import com.hoo.aoo.aar.adapter.out.persistence.entity.UserJpaEntity;
import com.hoo.aoo.aar.adapter.out.persistence.mapper.UserMapper;
import com.hoo.aoo.aar.adapter.out.persistence.repository.UserJpaRepository;
import com.hoo.aoo.aar.application.port.out.database.LoadUserPort;
import com.hoo.aoo.aar.application.port.out.database.SaveUserPort;
import com.hoo.aoo.aar.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserPersistenceAdapter implements LoadUserPort, SaveUserPort {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Override
    public boolean existByNickname(String nickname) {
        return userJpaRepository.findByNickname(nickname).isPresent();
    }

    @Override
    public User save(User user) {

        UserJpaEntity newUserJpaEntity = userMapper.mapToUserJpaEntity(user);

        userJpaRepository.save(newUserJpaEntity);

        return userMapper.mapToUserDomainEntity(newUserJpaEntity);
    }
}
