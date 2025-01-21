package com.hoo.aoo.aar.adapter.in.web.authn.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hoo.aoo.common.adapter.out.persistence.entity.SnsAccountJpaEntity;

import java.util.HashMap;
import java.util.Map;

public record SNSLoginResponse(
        String nickname,
        String accessToken,
        String provider,
        Boolean isFirstLogin
) {

    public static SNSLoginResponse from(Map<String, Object> attributes) {
        return new SNSLoginResponse(
                (String) attributes.get("nickname"),
                (String) attributes.get("accessToken"),
                (String) attributes.get("provider"),
                (Boolean) attributes.get("isFirstLogin"));
    }

    public static SNSLoginResponse of(SnsAccountJpaEntity snsAccountJpaEntity, String accessToken, boolean isFirstLogin) {

        String nickname = isFirstLogin? snsAccountJpaEntity.getNickname() : snsAccountJpaEntity.getUserEntity().getNickname();
        String provider = snsAccountJpaEntity.getSnsDomain().name().toUpperCase();

        return new SNSLoginResponse(
                nickname,
                accessToken,
                provider,
                isFirstLogin);
    }

    @JsonIgnore
    public Map<String, Object> getAttributes() {

        Map<String, Object> ret = new HashMap<>();

        ret.put("nickname", nickname);
        ret.put("accessToken", accessToken);
        ret.put("provider", provider);
        ret.put("isFirstLogin", isFirstLogin);

        return ret;
    }
}
