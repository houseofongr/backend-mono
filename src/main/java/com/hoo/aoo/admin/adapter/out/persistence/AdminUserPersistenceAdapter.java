package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.aar.adapter.out.persistence.repository.UserJpaRepository;
import com.hoo.aoo.admin.adapter.out.persistence.mapper.AdminUserMapper;
import com.hoo.aoo.admin.application.port.in.user.QueryUserInfoCommand;
import com.hoo.aoo.admin.application.port.in.user.QueryUserInfoResult;
import com.hoo.aoo.admin.application.port.out.user.FindUserPort;
import com.hoo.aoo.admin.application.port.out.user.SearchUserPort;
import com.hoo.aoo.admin.application.port.out.user.UpdateUserPort;
import com.hoo.aoo.admin.domain.user.User;
import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AdminUserPersistenceAdapter implements SearchUserPort, FindUserPort, UpdateUserPort {

    private final UserJpaRepository userJpaRepository;
    private final AdminUserMapper adminUserMapper;

    @Override
    public QueryUserInfoResult search(QueryUserInfoCommand command) {
        return adminUserMapper.mapToQueryResults(userJpaRepository.searchByCommand(command));
    }

    @Override
    public Optional<User> loadUser(Long id) {
        return userJpaRepository.findById(id).map(adminUserMapper::mapToDomainEntity);
    }

    @Override
    public boolean exist(Long id) {
        return userJpaRepository.existsById(id);
    }

    @Override
    public void updateUser(User user) {
        UserJpaEntity userJpaEntity = userJpaRepository.findById(user.getUserId().getId()).orElseThrow();
        userJpaEntity.update(user);
    }
}
