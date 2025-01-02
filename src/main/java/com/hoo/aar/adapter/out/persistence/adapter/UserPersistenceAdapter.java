package com.hoo.aar.adapter.out.persistence.adapter;

import com.hoo.aar.adapter.out.persistence.entity.UserJpaEntity;
import com.hoo.aar.adapter.out.persistence.mapper.UserMapper;
import com.hoo.aar.adapter.out.persistence.repository.UserJpaRepository;
import com.hoo.aar.application.port.out.database.LoadUserPort;
import com.hoo.aar.application.port.out.database.SaveUserPort;
import com.hoo.aar.common.enums.ErrorCode;
import com.hoo.aar.common.exception.AarException;
import com.hoo.aar.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserPersistenceAdapter implements LoadUserPort, SaveUserPort {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Override
    public void assertNotExistNickname(String nickname) {
        if (userJpaRepository.findByNickname(nickname).isPresent())
            throw new AarException(ErrorCode.NICK_NAME_CONFLICT);
    }

    @Override
    public User save(User user) {
        UserJpaEntity newUser = UserJpaEntity.regist(user);
        userJpaRepository.save(newUser);
        return userMapper.userJpaEntityToUser(newUser);
    }
}
