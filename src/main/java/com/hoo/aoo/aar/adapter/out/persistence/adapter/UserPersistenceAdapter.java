package com.hoo.aoo.aar.adapter.out.persistence.adapter;

import com.hoo.aoo.aar.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.hoo.aoo.aar.adapter.out.persistence.entity.UserJpaEntity;
import com.hoo.aoo.aar.adapter.out.persistence.mapper.UserMapper;
import com.hoo.aoo.aar.adapter.out.persistence.repository.SnsAccountJpaRepository;
import com.hoo.aoo.aar.adapter.out.persistence.repository.UserJpaRepository;
import com.hoo.aoo.aar.application.port.out.database.LoadUserPort;
import com.hoo.aoo.aar.application.port.out.database.SaveUserPort;
import com.hoo.aoo.aar.domain.SnsAccount;
import com.hoo.aoo.aar.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class UserPersistenceAdapter implements LoadUserPort, SaveUserPort {

    private final UserJpaRepository userJpaRepository;
    private final SnsAccountJpaRepository snsAccountJpaRepository;
    private final UserMapper userMapper;

    @Override
    public boolean existByNickname(String nickname) {
        return userJpaRepository.findByNickname(nickname).isPresent();
    }

    @Override
    public User save(User user) {
        List<SnsAccountJpaEntity> snsAccountEntities = snsAccountJpaRepository.findAllById(
                user.getSnsAccounts().stream().map(SnsAccount::getId).toList());

        UserJpaEntity newUserJpaEntity = userMapper.mapToUserJpaEntity(user, snsAccountEntities);

        userJpaRepository.save(newUserJpaEntity);

        return userMapper.mapToUserDomainEntity(newUserJpaEntity, snsAccountEntities);
    }
}
