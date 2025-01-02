package com.hoo.aar.adapter.in.web.authn.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2UserServiceDelegator extends DefaultOAuth2UserService {

    private final KakaoLoadUserService kakaoLoadUserService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User user = loadSuperUser(userRequest);

        switch (userRequest.getClientRegistration().getRegistrationId()) {
            case "kakao":
                return kakaoLoadUserService.load(user);
            default:
                throw new UnsupportedOperationException();
        }
    }

    OAuth2User loadSuperUser(OAuth2UserRequest userRequest) {
        return super.loadUser(userRequest);
    }
}
