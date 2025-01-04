package com.hoo.aoo.aar.adapter.out.persistence.mapper;

import com.hoo.aoo.aar.adapter.in.web.authn.security.dto.OAuth2Dto;
import com.hoo.aoo.aar.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.hoo.aoo.aar.adapter.out.persistence.entity.UserJpaEntity;
import com.hoo.aoo.common.enums.SnsDomain;
import com.hoo.aoo.aar.domain.SnsAccount;
import com.hoo.aoo.aar.domain.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public User mapToUserDomainEntity(UserJpaEntity userJpaEntity, List<SnsAccountJpaEntity> snsAccountJpaEntities) {
        List<SnsAccount> snsAccounts = snsAccountJpaEntities.stream().map(this::mapToSnsAccountDomainEntity).toList();
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

    public UserJpaEntity mapToUserJpaEntity(User user, List<SnsAccountJpaEntity> snsAccountEntities) {
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

    public SnsAccount mapToSnsAccountDomainEntity(SnsAccountJpaEntity snsAccountJpaEntity) {
        return new SnsAccount(
                snsAccountJpaEntity.getId(),
                snsAccountJpaEntity.getName(),
                snsAccountJpaEntity.getNickname(),
                snsAccountJpaEntity.getEmail(),
                snsAccountJpaEntity.getSnsId(),
                snsAccountJpaEntity.getSnsDomain(),
                null
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
                null
        );
    }

    public SnsAccount kakaoUserToSnsAccount(OAuth2Dto.KakaoUserInfo kakaoUser) {
        return SnsAccount.regist(
                kakaoUser.kakao_account().profile().nickname(),
                kakaoUser.kakao_account().email(),
                kakaoUser.id(),
                SnsDomain.KAKAO);
    }

}
