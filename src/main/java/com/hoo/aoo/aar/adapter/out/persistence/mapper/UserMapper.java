package com.hoo.aoo.aar.adapter.out.persistence.mapper;

import com.hoo.aoo.aar.application.port.in.user.QueryMyInfoResult;
import com.hoo.aoo.aar.domain.exception.InvalidPhoneNumberException;
import com.hoo.aoo.aar.domain.user.User;
import com.hoo.aoo.aar.domain.user.snsaccount.SnsAccount;
import com.hoo.aoo.common.adapter.in.web.DateTimeFormatters;
import com.hoo.aoo.common.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("AARUserMapper")
@RequiredArgsConstructor
public class UserMapper {

    private final SnsAccountMapper snsAccountMapper;

    public UserJpaEntity mapToNewJpaEntity(User user, List<SnsAccountJpaEntity> snsAccountJpaEntities) {

        UserJpaEntity userJpaEntity = new UserJpaEntity(null,
                user.getUserInfo().getRealName(),
                user.getUserInfo().getNickname(),
                user.getUserInfo().getEmail(),
                null,
                user.getAgreement().getTermsOfUseAgreement(),
                user.getAgreement().getPersonalInformationAgreement(),
                snsAccountJpaEntities);

        snsAccountJpaEntities.forEach(snsAccountJpaEntity -> snsAccountJpaEntity.setUserEntity(userJpaEntity));

        return userJpaEntity;
    }

    public User mapToDomainEntity(UserJpaEntity userJpaEntity) throws InvalidPhoneNumberException {
        List<SnsAccount> snsAccounts = userJpaEntity.getSnsAccountEntities().stream().map(snsAccountMapper::mapToDomainEntity).toList();

        return User.load(
                userJpaEntity.getId(),
                userJpaEntity.getRealName(),
                userJpaEntity.getNickname(),
                userJpaEntity.getEmail(),
                userJpaEntity.getTermsOfUseAgreement(),
                userJpaEntity.getPersonalInformationAgreement(),
                userJpaEntity.getCreatedTime(),
                userJpaEntity.getUpdatedTime(),
                snsAccounts
        );
    }

    public QueryMyInfoResult mapToQueryMyInfoResult(UserJpaEntity userJpaEntity, Integer homeCount, Integer soundSourceCount) {

        return new QueryMyInfoResult(
                userJpaEntity.getNickname(),
                userJpaEntity.getEmail(),
                DateTimeFormatters.ENGLISH_DATE.getFormatter().format(userJpaEntity.getCreatedTime()),
                userJpaEntity.getTermsOfUseAgreement(),
                userJpaEntity.getPersonalInformationAgreement(),
                homeCount,
                soundSourceCount,
                userJpaEntity.getSnsAccountEntities().stream().map(this::mapToSnsAccountInfo).toList()
        );
    }

    private QueryMyInfoResult.SnsAccountInfo mapToSnsAccountInfo(SnsAccountJpaEntity snsAccountJpaEntity) {
        return new QueryMyInfoResult.SnsAccountInfo(
                snsAccountJpaEntity.getSnsDomain().name(),
                snsAccountJpaEntity.getEmail()
        );
    }
}
