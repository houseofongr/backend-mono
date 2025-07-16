package com.hoo.main.application.port.in.authn;

public record VerifyEmailAuthnCodeResult(
        String message,
        Integer ttl
) {
}
