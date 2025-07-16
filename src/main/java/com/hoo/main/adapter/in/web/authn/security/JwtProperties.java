package com.hoo.main.adapter.in.web.authn.security;

public record JwtProperties(
        String secret,
        String issuer,
        Long expire
) {
}
