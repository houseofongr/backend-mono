package com.hoo.aar.domain.authn.dto;

public record Login() {
    public record Response(
            String username,
            String email,
            String provider
    ) {
    }
}
