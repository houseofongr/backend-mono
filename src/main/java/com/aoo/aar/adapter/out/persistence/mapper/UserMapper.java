package com.aoo.aar.adapter.out.persistence.mapper;

import com.aoo.aar.application.port.in.user.SearchMyInfoResult;
import com.aoo.common.adapter.in.web.DateTimeFormatters;
import com.aoo.common.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("AARUserMapper")
@RequiredArgsConstructor
public class UserMapper {

    public SearchMyInfoResult mapToQueryMyInfoResult(UserJpaEntity userJpaEntity, Integer homeCount, Integer soundSourceCount) {

        return new SearchMyInfoResult(
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

    private SearchMyInfoResult.SnsAccountInfo mapToSnsAccountInfo(SnsAccountJpaEntity snsAccountJpaEntity) {
        return new SearchMyInfoResult.SnsAccountInfo(
                snsAccountJpaEntity.getSnsDomain().name(),
                snsAccountJpaEntity.getEmail()
        );
    }
}
