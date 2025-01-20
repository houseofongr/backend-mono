package com.hoo.aoo.admin.application.port.in.user;

import org.springframework.data.domain.Page;

public record QueryUserInfoResult(
        Page<UserInfo> users
) {
    public record UserInfo(
            Long id,
            String name,
            String email,
            String phoneNumber,
            String registeredDate
    ) {

    }
}
