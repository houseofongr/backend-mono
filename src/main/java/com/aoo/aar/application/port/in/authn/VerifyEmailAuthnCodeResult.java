package com.aoo.aar.application.port.in.authn;

public record VerifyEmailAuthnCodeResult(
        String message,
        Integer ttl
) {
}
