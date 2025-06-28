package com.aoo.admin.adapter.out.persistence.mapper;

import com.aoo.admin.application.port.in.user.QueryUserInfoResult;
import com.aoo.admin.application.port.in.user.SearchUserResult;
import com.aoo.admin.domain.user.BusinessUser;
import com.aoo.admin.domain.user.User;
import com.aoo.admin.domain.user.snsaccount.SnsAccount;
import com.aoo.common.adapter.in.web.DateTimeFormatters;
import com.aoo.common.adapter.out.persistence.entity.BusinessUserJpaEntity;
import com.aoo.common.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final SnsAccountMapper snsAccountMapper;

    public QueryUserInfoResult.UserInfo mapToQueryResult(UserJpaEntity userJpaEntity) {
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

    public SearchUserResult.UserInfo mapToSearchResult(UserJpaEntity userJpaEntity) {
        return new SearchUserResult.UserInfo(
                userJpaEntity.getId(),
                userJpaEntity.getRealName(),
                userJpaEntity.getNickname(),
                userJpaEntity.getPhoneNumber(),
                userJpaEntity.getEmail(),
                userJpaEntity.getCreatedTime().toEpochSecond(),
                userJpaEntity.getTermsOfUseAgreement(),
                userJpaEntity.getPersonalInformationAgreement(),
                userJpaEntity.getSnsAccountEntities().stream().map(this::mapToSearchResult).toList()
        );
    }

    private SearchUserResult.SnsAccountInfo mapToSearchResult(SnsAccountJpaEntity snsAccountJpaEntity) {
        return new SearchUserResult.SnsAccountInfo(
                snsAccountJpaEntity.getSnsDomain(),
                snsAccountJpaEntity.getEmail()
        );
    }

    public User mapToDomainEntity(UserJpaEntity userJpaEntity) {
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

    public BusinessUser mapToBusinessUserEntity(BusinessUserJpaEntity businessUserJpaEntity) {
        return BusinessUser.load(businessUserJpaEntity.getId(),
                businessUserJpaEntity.getNickname(),
                businessUserJpaEntity.getEmail(),
                businessUserJpaEntity.getTermsOfUseAgreement(),
                businessUserJpaEntity.getPersonalInformationAgreement(),
                businessUserJpaEntity.getCreatedTime(),
                businessUserJpaEntity.getUpdatedTime()
        );
    }
}
