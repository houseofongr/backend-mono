package com.hoo.aoo.admin.application.port.in.user;

import java.util.List;

public record QueryUserResult(
        List<User> users
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
