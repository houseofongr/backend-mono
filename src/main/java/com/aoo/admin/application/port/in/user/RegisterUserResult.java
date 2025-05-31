package com.aoo.admin.application.port.in.user;

public record RegisterUserResult(
        Long userId,
        String nickname,
        String accessToken
) {
}
