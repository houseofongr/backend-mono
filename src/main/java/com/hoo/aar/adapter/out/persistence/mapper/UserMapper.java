package com.hoo.aar.adapter.out.persistence.mapper;

import com.hoo.aar.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.hoo.aar.adapter.out.persistence.entity.UserJpaEntity;
import com.hoo.aar.domain.SnsAccount;
import com.hoo.aar.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

import static org.mapstruct.NullValuePropertyMappingStrategy.SET_TO_NULL;

@Mapper(componentModel = "spring")
public interface UserMapper {

    default User mapToUserDomainEntity(UserJpaEntity userJpaEntity, List<SnsAccountJpaEntity> snsAccountJpaEntities) {
        return new User(
                userJpaEntity.getId(),
                userJpaEntity.getName(),
                userJpaEntity.getNickname(),
                userJpaEntity.getPhoneNumber(),
                userJpaEntity.getRecordAgreement(),
                userJpaEntity.getPersonalInformationAgreement(),
                snsAccountJpaEntities.stream().map(this::snsAccountJpaEntityToSnsAccount).toList()
                );
    }

    default UserJpaEntity mapToUserJpaEntity(User user, List<SnsAccountJpaEntity> snsAccountEntities){
        return new UserJpaEntity(
                user.getId(),
                user.getName(),
                user.getNickname(),
                user.getPhoneNumber(),
                user.getRecordAgreement(),
                user.getPersonalInformationAgreement(),
                snsAccountEntities
        );
    }

    @Mapping(target = "user", ignore = true)
    SnsAccount snsAccountJpaEntityToSnsAccount(SnsAccountJpaEntity snsAccountJpaEntity);

}
