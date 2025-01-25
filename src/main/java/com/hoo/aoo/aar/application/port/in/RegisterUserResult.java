package com.hoo.aoo.aar.application.port.in;

public record RegisterUserResult(
        Long userId,
        String nickname,
        String accessToken
) {
}
