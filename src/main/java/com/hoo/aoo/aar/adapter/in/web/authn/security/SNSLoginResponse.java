package com.hoo.aoo.aar.adapter.in.web.authn.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hoo.aoo.aar.domain.user.User;
import com.hoo.aoo.aar.domain.user.snsaccount.SnsAccount;
import com.hoo.aoo.aar.domain.user.snsaccount.SnsDomain;

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

    public static SNSLoginResponse from(SnsAccount snsAccount, String accessToken) {

        return new SNSLoginResponse(
                snsAccount.getSnsAccountInfo().getNickname(),
                accessToken,
                snsAccount.getSnsAccountId().getSnsDomain().name().toUpperCase(),
                true
        );
    }

    public static SNSLoginResponse from(User user, String accessToken, SnsDomain snsDomain) {

        return new SNSLoginResponse(
                user.getUserInfo().getNickname(),
                accessToken,
                snsDomain.name().toUpperCase(),
                false
        );
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
