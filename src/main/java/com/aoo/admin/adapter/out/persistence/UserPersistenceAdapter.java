package com.aoo.admin.adapter.out.persistence;

import com.aoo.aar.adapter.out.persistence.repository.SnsAccountJpaRepository;
import com.aoo.aar.adapter.out.persistence.repository.UserJpaRepository;
import com.aoo.aar.application.service.AarErrorCode;
import com.aoo.aar.application.service.AarException;
import com.aoo.admin.adapter.out.persistence.mapper.UserMapper;
import com.aoo.admin.application.port.in.user.*;
import com.aoo.admin.application.port.out.user.FindUserPort;
import com.aoo.admin.application.port.out.user.SaveUserPort;
import com.aoo.admin.application.port.out.user.SearchUserPort;
import com.aoo.admin.application.port.out.user.UpdateUserPort;
import com.aoo.admin.domain.user.DeletedUser;
import com.aoo.admin.domain.user.User;
import com.aoo.common.adapter.out.persistence.entity.DeletedUserJpaEntity;
import com.aoo.common.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import com.aoo.common.adapter.out.persistence.repository.DeletedUserJpaRepository;
import com.aoo.common.adapter.out.persistence.repository.HomeJpaRepository;
import com.aoo.common.adapter.out.persistence.repository.ItemJpaRepository;
import com.aoo.common.adapter.out.persistence.repository.SoundSourceJpaRepository;
import com.aoo.common.application.port.in.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements SaveUserPort, SaveDeletedUserPort, SearchUserPort, FindUserPort, UpdateUserPort, DeleteUserPort {

    private final UserJpaRepository userJpaRepository;
    private final SnsAccountJpaRepository snsAccountJpaRepository;
    private final DeletedUserJpaRepository deletedUserJpaRepository;
    private final SoundSourceJpaRepository soundSourceJpaRepository;
    private final ItemJpaRepository itemJpaRepository;
    private final HomeJpaRepository homeJpaRepository;
    private final UserMapper userMapper;

    @Override
    public Long save(User user) {

        List<SnsAccountJpaEntity> snsAccountJpaEntities = user.getSnsAccounts().stream().map(snsAccount ->
                snsAccountJpaRepository.findWithUserEntity(snsAccount.getSnsAccountId().getSnsDomain(), snsAccount.getSnsAccountId().getSnsId())
                        .orElseThrow(() -> new AarException(AarErrorCode.SNS_ACCOUNT_NOT_FOUND))
        ).toList();

        UserJpaEntity entity = UserJpaEntity.create(user, snsAccountJpaEntities);

        userJpaRepository.save(entity);

        return entity.getId();
    }

    @Override
    public Long saveDeletedUser(DeletedUser deletedUser) {
        DeletedUserJpaEntity deletedUserJpaEntity = DeletedUserJpaEntity.create(deletedUser);
        deletedUserJpaRepository.save(deletedUserJpaEntity);
        return deletedUserJpaEntity.getId();
    }

    @Override
    public QueryUserInfoResult query(QueryUserInfoCommand command) {
        Page<QueryUserInfoResult.UserInfo> userInfosPages = userJpaRepository.searchAll(command).map(userMapper::mapToQueryResult);
        return new QueryUserInfoResult(userInfosPages.getContent(), Pagination.of(userInfosPages));
    }

    @Override
    public SearchUserResult search(SearchUserCommand command) {
        Page<SearchUserResult.UserInfo> userInfosPages = userJpaRepository.searchAll(command).map(userMapper::mapToSearchResult);
        return new SearchUserResult(userInfosPages.getContent(), Pagination.of(userInfosPages));
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
    public void deleteUser(User user) {
        Long userId = user.getUserId().getId();

        snsAccountJpaRepository.deleteAll(snsAccountJpaRepository.findAllByUserId(userId));
        soundSourceJpaRepository.deleteAll(soundSourceJpaRepository.findAllByUserId(userId));
        itemJpaRepository.deleteAll(itemJpaRepository.findAllByUserId(userId));
        homeJpaRepository.deleteAll(homeJpaRepository.findAllByUserId(userId));

        userJpaRepository.deleteById(userId);
    }
}
