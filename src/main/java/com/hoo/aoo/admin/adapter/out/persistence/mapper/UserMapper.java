package com.hoo.aoo.admin.adapter.out.persistence.mapper;

import com.hoo.aoo.admin.application.port.in.user.SearchUserResult;
import com.hoo.aoo.admin.domain.user.User;
import com.hoo.aoo.admin.domain.user.snsaccount.SnsAccount;
import com.hoo.aoo.common.adapter.in.web.DateTimeFormatters;
import com.hoo.aoo.common.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import com.hoo.aoo.common.application.port.in.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final SnsAccountMapper snsAccountMapper;

    public SearchUserResult.UserInfo mapToQueryResult(UserJpaEntity userJpaEntity) {
        return new SearchUserResult.UserInfo(
                userJpaEntity.getId(),
                userJpaEntity.getRealName(),
                userJpaEntity.getNickname(),
                userJpaEntity.getPhoneNumber(),
                userJpaEntity.getEmail(),
                userJpaEntity.getCreatedTime().toEpochSecond(),
                userJpaEntity.getTermsOfUseAgreement(),
                userJpaEntity.getPersonalInformationAgreement(),
                userJpaEntity.getSnsAccountEntities().stream().map(this::mapToQueryResult).toList()
        );
    }

    private SearchUserResult.SnsAccountInfo mapToQueryResult(SnsAccountJpaEntity snsAccountJpaEntity) {
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
}
