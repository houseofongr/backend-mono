package com.hoo.aar.adapter.in.web.authn.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hoo.aar.adapter.out.persistence.entity.SnsAccountJpaEntity;

import java.util.HashMap;
import java.util.Map;

public record SNSLoginResponse(
        String username,
        String accessToken,
        String provider,
        Boolean isFirstLogin
) {

    public static SNSLoginResponse from(Map<String, Object> attributes) {
        return new SNSLoginResponse(
                (String) attributes.get("username"),
                (String) attributes.get("accessToken"),
                (String) attributes.get("provider"),
                (Boolean) attributes.get("isFirstLogin"));
    }

    public static SNSLoginResponse of(SnsAccountJpaEntity accountEntity, String accessToken, boolean isFirstLogin) {
        return new SNSLoginResponse(
                accountEntity.getName(),
                accessToken,
                accountEntity.getSnsDomain().name(),
                isFirstLogin);
    }

    @JsonIgnore
    public Map<String, Object> getAttributes() {

        Map<String, Object> ret = new HashMap<>();

        ret.put("username", username);
        ret.put("accessToken", accessToken);
        ret.put("provider", provider);
        ret.put("isFirstLogin", isFirstLogin);

        return ret;
    }
}
