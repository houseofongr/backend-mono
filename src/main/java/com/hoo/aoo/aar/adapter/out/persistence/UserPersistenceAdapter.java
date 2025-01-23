package com.hoo.aoo.aar.adapter.out.persistence;

import com.hoo.aoo.common.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import com.hoo.aoo.aar.adapter.out.persistence.mapper.UserMapper;
import com.hoo.aoo.aar.adapter.out.persistence.repository.SnsAccountJpaRepository;
import com.hoo.aoo.aar.adapter.out.persistence.repository.UserJpaRepository;
import com.hoo.aoo.aar.application.port.out.database.FindUserPort;
import com.hoo.aoo.aar.application.port.out.database.SaveUserPort;
import com.hoo.aoo.aar.domain.Name;
import com.hoo.aoo.aar.domain.account.SnsAccountId;
import com.hoo.aoo.aar.domain.exception.InvalidPhoneNumberException;
import com.hoo.aoo.aar.domain.user.Agreement;
import com.hoo.aoo.aar.domain.user.UserId;
import com.hoo.aoo.aar.domain.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class UserPersistenceAdapter implements FindUserPort, SaveUserPort {

    private final UserJpaRepository userJpaRepository;
    private final SnsAccountJpaRepository snsAccountJpaRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<User> find(Long id) throws InvalidPhoneNumberException {
        Optional<UserJpaEntity> optional = userJpaRepository.findById(id);

        if (optional.isEmpty()) return Optional.empty();

        UserJpaEntity jpaEntity = optional.get();

        List<SnsAccountJpaEntity> snsAccountJpaEntities = snsAccountJpaRepository.findAllByUserId(jpaEntity.getId());

        Agreement agreement = new Agreement(jpaEntity.getPersonalInformationAgreement(), jpaEntity.getTermsOfUseAgreement());

        UserId phoneNumber = new UserId(jpaEntity.getPhoneNumber());

        List<SnsAccountId> snsAccountIds = snsAccountJpaEntities.stream()
                .map(snsAccountJpaEntity -> new SnsAccountId(snsAccountJpaEntity.getSnsDomain(), snsAccountJpaEntity.getSnsId())).toList();

        Name name = new Name(jpaEntity.getRealName(), jpaEntity.getNickname());

        return Optional.of(userMapper.mapToDomainEntity(agreement, phoneNumber, snsAccountIds, name));
    }

    @Override
    public void save(User user) {

        List<SnsAccountJpaEntity> snsAccountJpaEntities = user.getSnsAccounts().stream().map(snsAccountId ->
                snsAccountJpaRepository.findWithUserEntity(
                        snsAccountId.getSnsDomain(), snsAccountId.getSnsId()).get()).toList();

        UserJpaEntity entity = userMapper.mapToNewJpaEntity(user, snsAccountJpaEntities);

        userJpaRepository.save(entity);
    }
}
