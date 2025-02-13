package com.hoo.aoo.aar.adapter.out.persistence.mapper;

import com.hoo.aoo.aar.application.port.in.user.QueryMyInfoResult;
import com.hoo.aoo.common.adapter.in.web.DateTimeFormatters;
import com.hoo.aoo.common.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("AARUserMapper")
@RequiredArgsConstructor
public class UserMapper {

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
