package com.aoo.admin.application.port.in.user;

import com.aoo.admin.domain.user.snsaccount.SnsDomain;
import com.aoo.common.application.port.in.Pagination;

import java.util.List;

public record QueryUserInfoResult(
        List<UserInfo> users,
        Pagination pagination
) {
    public record UserInfo(
            Long id,
            String realName,
            String nickName,
            String phoneNumber,
            String registeredDate,
            Boolean termsOfUseAgreement,
            Boolean personalInformationAgreement,
            List<SnsAccountInfo> snsAccounts
    ) {

    }

    public record SnsAccountInfo(
            SnsDomain domain,
            String email
    ) {

    }
}
