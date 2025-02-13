package com.hoo.aoo.aar.application.port.in.authn;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hoo.aoo.admin.domain.user.User;
import com.hoo.aoo.admin.domain.user.snsaccount.SnsAccount;
import com.hoo.aoo.admin.domain.user.snsaccount.SnsDomain;

import java.util.HashMap;
import java.util.Map;

public record SNSLoginResult(
        String nickname,
        String accessToken,
        String provider,
        Boolean isFirstLogin
) {

    public static SNSLoginResult from(Map<String, Object> attributes) {
        return new SNSLoginResult(
                (String) attributes.get("nickname"),
                (String) attributes.get("accessToken"),
                (String) attributes.get("provider"),
                (Boolean) attributes.get("isFirstLogin"));
    }

    public static SNSLoginResult from(SnsAccount snsAccount, String accessToken) {

        return new SNSLoginResult(
                snsAccount.getSnsAccountInfo().getNickname(),
                accessToken,
                snsAccount.getSnsAccountId().getSnsDomain().name().toUpperCase(),
                true
        );
    }

    public static SNSLoginResult from(User user, String accessToken, SnsDomain snsDomain) {

        return new SNSLoginResult(
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
