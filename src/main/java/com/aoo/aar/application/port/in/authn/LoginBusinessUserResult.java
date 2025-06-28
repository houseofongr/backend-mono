package com.aoo.aar.application.port.in.authn;

public record LoginBusinessUserResult(
        String message,
        Long userId,
        String email,
        String nickname,
        String accessToken
) {
}
