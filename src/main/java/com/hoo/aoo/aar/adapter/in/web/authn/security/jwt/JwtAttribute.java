package com.hoo.aoo.aar.adapter.in.web.authn.security.jwt;

public record JwtAttribute(
        String secret,
        String issuer,
        Long expire
) {
}
