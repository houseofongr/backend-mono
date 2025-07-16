package com.hoo.main.application.port.in.authn;

public record CreateEmailAuthnCodeResult(
        String message,
        Integer ttl
) {
}
