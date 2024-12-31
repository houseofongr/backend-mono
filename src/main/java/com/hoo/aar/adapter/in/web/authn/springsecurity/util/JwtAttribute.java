package com.hoo.aar.adapter.in.web.authn.springsecurity.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public record JwtAttribute(
        String secret,
        String issuer,
        Long expire
) {
}
