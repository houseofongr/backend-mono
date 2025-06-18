package com.aoo.aar.application.port.in.authn;

public record CreateEmailAuthnCodeResult(
        String message,
        Integer ttl
) {
}
