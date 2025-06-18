package com.aoo.aar.adapter.out.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RedisKeys {
    EMAIL_AUTHN_CODE_PREFIX("email-authn-code:");

    private final String key;
}
