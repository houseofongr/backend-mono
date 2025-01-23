package com.hoo.aoo.aar.adapter.out.persistence.mapper;

import com.hoo.aoo.common.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import com.hoo.aoo.aar.domain.DateInfo;
import com.hoo.aoo.aar.domain.Name;
import com.hoo.aoo.aar.domain.account.SnsAccount;
import com.hoo.aoo.aar.domain.account.SnsAccountId;
import com.hoo.aoo.aar.domain.user.Agreement;
import com.hoo.aoo.aar.domain.user.UserId;
import com.hoo.aoo.aar.domain.user.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public UserJpaEntity mapToNewJpaEntity(User user, List<SnsAccountJpaEntity> snsAccountJpaEntities) {

        UserJpaEntity userJpaEntity = new UserJpaEntity(
                null,
                user.getName().getRealName(),
                user.getName().getNickname(),
                user.getPhoneNumber().getNumber(),
                user.getAgreement().getTermsOfUseAgreement(),
                user.getAgreement().getPersonalInformationAgreement(),
                snsAccountJpaEntities
        );

        snsAccountJpaEntities.forEach(snsAccountJpaEntity -> snsAccountJpaEntity.setUserEntity(userJpaEntity));

        return userJpaEntity;
    }

    public SnsAccountJpaEntity mapToNewJpaEntity(SnsAccount snsAccount) {

        return mapToJpaEntity(null,snsAccount,null);
    }

    public SnsAccountJpaEntity mapToJpaEntity(Long id, SnsAccount snsAccount, UserJpaEntity userJpaEntity) {

        return new SnsAccountJpaEntity(
                id,
                snsAccount.getName().getRealName(),
                snsAccount.getName().getNickname(),
                snsAccount.getEmail(),
                snsAccount.getSnsAccountId().getSnsId(),
                snsAccount.getSnsAccountId().getSnsDomain(),
                userJpaEntity
        );
    }

    public SnsAccount mapToDomainEntity(String email, SnsAccountId snsAccountId, Name name, UserId userId, DateInfo dateInfo) {
        return new SnsAccount(email, snsAccountId, name, userId, dateInfo);
    }

    public User mapToDomainEntity(Agreement agreement, UserId phoneNumber, List<SnsAccountId> snsAccountIds, Name name) {
        return new User(agreement,phoneNumber,snsAccountIds,name);
    }
}
