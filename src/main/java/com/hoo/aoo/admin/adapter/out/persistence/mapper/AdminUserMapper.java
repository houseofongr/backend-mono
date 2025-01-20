package com.hoo.aoo.admin.adapter.out.persistence.mapper;

import com.hoo.aoo.aar.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.hoo.aoo.aar.adapter.out.persistence.entity.UserJpaEntity;
import com.hoo.aoo.admin.application.port.in.user.QueryUserInfoResult;
import com.hoo.aoo.common.adapter.in.web.DateTimeFormatters;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class AdminUserMapper {

    public QueryUserInfoResult mapToQueryResults(Page<UserJpaEntity> userJpaEntities) {
        return new QueryUserInfoResult(userJpaEntities.map(this::mapToQueryResult));

    }

    private QueryUserInfoResult.UserInfo mapToQueryResult(UserJpaEntity userJpaEntity) {
        return new QueryUserInfoResult.UserInfo(
                userJpaEntity.getId(),
                userJpaEntity.getRealName(),
                userJpaEntity.getNickname(),
                userJpaEntity.getPhoneNumber(),
                DateTimeFormatters.DOT_DATE.getFormatter().format(userJpaEntity.getCreatedTime()),
                userJpaEntity.getTermsOfUseAgreement(),
                userJpaEntity.getPersonalInformationAgreement(),
                userJpaEntity.getSnsAccountEntities().stream().map(this::mapToQueryResult).toList()
        );
    }

    private QueryUserInfoResult.SnsAccountInfo mapToQueryResult(SnsAccountJpaEntity snsAccountJpaEntity) {
        return new QueryUserInfoResult.SnsAccountInfo(
                snsAccountJpaEntity.getSnsDomain(),
                snsAccountJpaEntity.getEmail()
        );
    }
}
