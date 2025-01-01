package com.hoo.aar.adapter.in.web.authn;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hoo.aar.adapter.out.persistence.entity.SnsAccountJpaEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.beans.Encoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public record Login() {

    public record Response(
            String username,
            String accessToken,
            String provider,
            Boolean isFirstLogin
    ) {

        public static Response of(SnsAccountJpaEntity accountEntity, String accessToken, boolean isFirstLogin) {
            return new Response(
                    accountEntity.getName(),
                    accessToken,
                    accountEntity.getSnsDomain().name(),
                    isFirstLogin);
        }
        
        public static Response of(Map<String, Object> attributes) {
            return new Response(
                    (String) attributes.get("username"),
                    (String) attributes.get("accessToken"),
                    (String) attributes.get("provider"),
                    (Boolean) attributes.get("isFirstLogin"));
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
}
