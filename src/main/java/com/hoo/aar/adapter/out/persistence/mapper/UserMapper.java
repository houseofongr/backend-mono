package com.hoo.aar.adapter.out.persistence.mapper;

import com.hoo.aar.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.hoo.aar.adapter.out.persistence.entity.UserJpaEntity;
import com.hoo.aar.domain.SnsAccount;
import com.hoo.aar.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    default User mapToUserDomainEntity(UserJpaEntity userJpaEntity, List<SnsAccountJpaEntity> snsAccountJpaEntities) {
        List<SnsAccount> snsAccounts = snsAccountJpaEntities.stream().map(this::snsAccountJpaEntityToSnsAccount).toList();
        User user = new User(
                userJpaEntity.getId(),
                userJpaEntity.getName(),
                userJpaEntity.getNickname(),
                userJpaEntity.getPhoneNumber(),
                userJpaEntity.getRecordAgreement(),
                userJpaEntity.getPersonalInformationAgreement(),
                snsAccounts
        );

        snsAccounts.forEach(snsAccount -> snsAccount.setUser(user));

        return user;
    }

    default UserJpaEntity mapToUserJpaEntity(User user, List<SnsAccountJpaEntity> snsAccountEntities) {
        UserJpaEntity userJpaEntity = new UserJpaEntity(
                user.getId(),
                user.getName(),
                user.getNickname(),
                user.getPhoneNumber(),
                user.getRecordAgreement(),
                user.getPersonalInformationAgreement(),
                snsAccountEntities
        );

        snsAccountEntities.forEach(snsAccountJpaEntity -> snsAccountJpaEntity.setUserEntity(userJpaEntity));

        return userJpaEntity;
    }

    @Mapping(target = "user", ignore = true)
    SnsAccount snsAccountJpaEntityToSnsAccount(SnsAccountJpaEntity snsAccountJpaEntity);

}
