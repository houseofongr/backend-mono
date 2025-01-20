package com.hoo.aoo.admin.application.port.in.user;

import com.hoo.aoo.aar.domain.account.SnsAccount;
import com.hoo.aoo.aar.domain.account.SnsAccountId;
import com.hoo.aoo.aar.domain.account.SnsDomain;
import org.springframework.data.domain.Page;

import java.util.List;

public record QueryUserInfoResult(
        Page<UserInfo> users
) {
    public record UserInfo(
            Long id,
            String realName,
            String nickName,
            String phoneNumber,
            String registeredDate,
            List<SnsAccountInfo> snsAccounts
    ) {

    }

    public record SnsAccountInfo(
            SnsDomain domain,
            String email
    ) {

    }
}
