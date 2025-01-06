package com.hoo.aoo.aar.adapter.out.persistence.mapper;

import com.hoo.aoo.aar.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.hoo.aoo.aar.adapter.out.persistence.entity.UserJpaEntity;
import com.hoo.aoo.aar.domain.SnsAccount;
import com.hoo.aoo.aar.domain.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public User mapToUserDomainEntity(UserJpaEntity userJpaEntity) {

        List<SnsAccount> snsAccounts = userJpaEntity.getSnsAccountEntities().stream()
                .map(this::mapToSnsAccountDomainEntity).toList();

        return new User(
                userJpaEntity.getId(),
                userJpaEntity.getName(),
                userJpaEntity.getNickname(),
                userJpaEntity.getPhoneNumber(),
                userJpaEntity.getRecordAgreement(),
                userJpaEntity.getPersonalInformationAgreement(),
                snsAccounts);
    }

    public UserJpaEntity mapToUserJpaEntity(User user) {

        List<SnsAccountJpaEntity> snsAccountJpaEntities = user.getSnsAccounts().stream()
                .map(this::mapToSnsAccountJpaEntity).toList();

        return new UserJpaEntity(
                user.getId(),
                user.getName(),
                user.getNickname(),
                user.getPhoneNumber(),
                user.getRecordAgreement(),
                user.getPersonalInformationAgreement(),
                snsAccountJpaEntities
        );

    }

    public SnsAccount mapToSnsAccountDomainEntity(SnsAccountJpaEntity snsAccountJpaEntity) {

        return new SnsAccount(
                snsAccountJpaEntity.getId(),
                snsAccountJpaEntity.getName(),
                snsAccountJpaEntity.getNickname(),
                snsAccountJpaEntity.getEmail(),
                snsAccountJpaEntity.getSnsId(),
                snsAccountJpaEntity.getSnsDomain(),
                snsAccountJpaEntity.getUserEntity() == null ? null : mapToTempUserDomainEntity(snsAccountJpaEntity.getUserEntity())
        );
    }

    public SnsAccountJpaEntity mapToSnsAccountJpaEntity(SnsAccount snsAccount) {

        return new SnsAccountJpaEntity(
                snsAccount.getId(),
                snsAccount.getName(),
                snsAccount.getNickname(),
                snsAccount.getEmail(),
                snsAccount.getSnsId(),
                snsAccount.getSnsDomain(),
                snsAccount.getUser() == null ? null : mapToTempUserJpaEntity(snsAccount.getUser())
        );
    }

    private User mapToTempUserDomainEntity(UserJpaEntity userJpaEntity) {
        return new User(
                userJpaEntity.getId(),
                userJpaEntity.getName(),
                userJpaEntity.getNickname(),
                userJpaEntity.getPhoneNumber(),
                userJpaEntity.getRecordAgreement(),
                userJpaEntity.getPersonalInformationAgreement(),
                null);
    }

    private UserJpaEntity mapToTempUserJpaEntity(User user) {

        return new UserJpaEntity(
                user.getId(),
                user.getName(),
                user.getNickname(),
                user.getPhoneNumber(),
                user.getRecordAgreement(),
                user.getPersonalInformationAgreement(),
                null
        );
    }

}
