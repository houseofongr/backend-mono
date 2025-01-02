package com.hoo.aar.adapter.in.web.authn.security.service;

import org.springframework.security.oauth2.core.user.OAuth2User;

public interface LoadUserService {
    OAuth2User load(OAuth2User user);
}
