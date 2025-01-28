package com.hoo.aoo.aar.adapter.in.web.authn.security;

public record JwtAttribute(
        String secret,
        String issuer,
        Long expire
) {
}
