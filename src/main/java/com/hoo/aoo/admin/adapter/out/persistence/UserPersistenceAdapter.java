package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.aar.adapter.out.persistence.repository.UserJpaRepository;
import com.hoo.aoo.admin.adapter.out.persistence.mapper.UserMapper;
import com.hoo.aoo.admin.application.port.in.user.DeleteUserPort;
import com.hoo.aoo.admin.application.port.in.user.QueryUserInfoCommand;
import com.hoo.aoo.admin.application.port.in.user.QueryUserInfoResult;
import com.hoo.aoo.admin.application.port.in.user.SaveDeletedUserPort;
import com.hoo.aoo.admin.application.port.out.user.FindUserPort;
import com.hoo.aoo.admin.application.port.out.user.SearchUserPort;
import com.hoo.aoo.admin.application.port.out.user.UpdateUserPort;
import com.hoo.aoo.admin.domain.user.DeletedUser;
import com.hoo.aoo.admin.domain.user.User;
import com.hoo.aoo.common.adapter.out.persistence.entity.DeletedUserJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.repository.DeletedUserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements SaveDeletedUserPort, SearchUserPort, FindUserPort, UpdateUserPort, DeleteUserPort {

    private final UserJpaRepository userJpaRepository;
    private final DeletedUserJpaRepository deletedUserJpaRepository;
    private final UserMapper userMapper;

    @Override
    public QueryUserInfoResult search(QueryUserInfoCommand command) {
        return userMapper.mapToQueryResults(userJpaRepository.searchByCommand(command));
    }

    @Override
    public Optional<User> loadUser(Long id) {
        return userJpaRepository.findById(id).map(userMapper::mapToDomainEntity);
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

    @Override
    public Long saveDeletedUser(DeletedUser deletedUser) {
        DeletedUserJpaEntity deletedUserJpaEntity = DeletedUserJpaEntity.create(deletedUser);
        deletedUserJpaRepository.save(deletedUserJpaEntity);
        return deletedUserJpaEntity.getId();
    }

    @Override
    public void deleteUser(User user) {

    }
}
