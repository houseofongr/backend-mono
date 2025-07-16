package com.hoo.main.application.service.authn;

import org.springframework.security.oauth2.core.user.OAuth2User;

public interface LoadSnsAccountService {
    OAuth2User load(OAuth2User user);
}
