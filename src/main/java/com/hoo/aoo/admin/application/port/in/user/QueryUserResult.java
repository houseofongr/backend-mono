package com.hoo.aoo.admin.application.port.in.user;

import org.springframework.data.domain.Page;

public record QueryUserResult(
        Page<User> users
) {
    public record User(
            Long id,
            String name,
            String email,
            String phoneNumber,
            String registeredDate
    ) {

    }
}
