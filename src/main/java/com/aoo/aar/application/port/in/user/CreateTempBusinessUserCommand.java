package com.aoo.aar.application.port.in.user;

public record CreateTempBusinessUserCommand(
        String email,
        String password,
        String nickname
) {
}
