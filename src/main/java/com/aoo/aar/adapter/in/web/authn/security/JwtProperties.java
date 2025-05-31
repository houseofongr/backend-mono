package com.aoo.aar.adapter.in.web.authn.security;

public record JwtProperties(
        String secret,
        String issuer,
        Long expire
) {
}
