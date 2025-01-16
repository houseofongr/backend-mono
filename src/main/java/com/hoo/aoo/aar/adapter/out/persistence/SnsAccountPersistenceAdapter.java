package com.hoo.aoo.aar.adapter.out.persistence;

import com.hoo.aoo.aar.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.hoo.aoo.aar.adapter.out.persistence.mapper.UserMapper;
import com.hoo.aoo.aar.adapter.out.persistence.repository.SnsAccountJpaRepository;
import com.hoo.aoo.aar.application.port.out.database.FindSnsAccountPort;
import com.hoo.aoo.aar.application.port.out.database.SaveSnsAccountPort;
import com.hoo.aoo.aar.domain.DateInfo;
import com.hoo.aoo.aar.domain.Name;
import com.hoo.aoo.aar.domain.account.SnsAccount;
import com.hoo.aoo.aar.domain.account.SnsAccountId;
import com.hoo.aoo.aar.domain.exception.InvalidPhoneNumberException;
import com.hoo.aoo.aar.domain.user.PhoneNumber;
import com.hoo.aoo.aar.domain.account.SnsDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SnsAccountPersistenceAdapter implements FindSnsAccountPort, SaveSnsAccountPort {

    private final SnsAccountJpaRepository snsAccountJpaRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<SnsAccount> load(SnsDomain domain, String snsId) throws InvalidPhoneNumberException {

        return getSnsAccountFromJpaEntity(snsAccountJpaRepository.findWithUserEntity(domain, snsId));
    }

    @Override
    public Optional<SnsAccount> load(Long id) throws InvalidPhoneNumberException {

        return getSnsAccountFromJpaEntity(snsAccountJpaRepository.findById(id));
    }

    @Override
    public void save(SnsAccount snsAccount) {

        SnsAccountJpaEntity newSnsAccountJpaEntity = userMapper.mapToNewJpaEntity(snsAccount);

        snsAccountJpaRepository.save(newSnsAccountJpaEntity);
    }

    private Optional<SnsAccount> getSnsAccountFromJpaEntity(Optional<SnsAccountJpaEntity> optional) throws InvalidPhoneNumberException {
        if (optional.isEmpty()) return Optional.empty();

        SnsAccountJpaEntity jpaEntity = optional.get();

        String email = jpaEntity.getEmail();

        SnsAccountId snsAccountId = new SnsAccountId(jpaEntity.getSnsDomain(), jpaEntity.getSnsId());

        Name name = new Name(jpaEntity.getRealName(), jpaEntity.getNickname());

        PhoneNumber userId = jpaEntity.getUserEntity() == null? null :
                new PhoneNumber(jpaEntity.getUserEntity().getPhoneNumber());

        DateInfo dateInfo = new DateInfo(jpaEntity.getCreatedTime(),jpaEntity.getUpdatedTime());

        return Optional.of(userMapper.mapToDomainEntity(email, snsAccountId, name, userId, dateInfo));
    }
}
