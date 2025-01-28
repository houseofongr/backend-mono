package com.hoo.aoo.aar.application.port.in.authn;

public record RegisterUserResult(
        Long userId,
        String nickname,
        String accessToken
) {
}
