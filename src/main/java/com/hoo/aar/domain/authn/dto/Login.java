package com.hoo.aar.domain.authn.dto;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public record Login() {

    public record Response(
            String username,
            String accessToken,
            String provider,
            Boolean isFirstLogin
    ) {
        public static MultiValueMap<String, String> getQueryParams(Response dto) {
            MultiValueMap<String, String> ret = new LinkedMultiValueMap<>();
            ret.add("username",dto.username);
            ret.add("accessToken",dto.accessToken);
            ret.add("provider",dto.provider);
            ret.add("isFirstLogin", String.valueOf(dto.isFirstLogin));
            return ret;
        }
    }
}
