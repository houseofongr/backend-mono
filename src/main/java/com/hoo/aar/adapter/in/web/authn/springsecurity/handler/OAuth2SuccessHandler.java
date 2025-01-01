package com.hoo.aar.adapter.in.web.authn.springsecurity.handler;

import com.hoo.aar.adapter.in.web.authn.Login;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Value("${security.frontend-redirect-uri}")
    private String redirectUri;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
        Login.Response dto = Login.Response.of(user.getAttributes());

        String redirectUrl = UriComponentsBuilder.fromUriString(redirectUri)
                .queryParam("username", URLEncoder.encode(dto.username(), StandardCharsets.UTF_8))
                .queryParam("accessToken",dto.accessToken())
                .queryParam("provider",URLEncoder.encode(dto.provider(), StandardCharsets.UTF_8))
                .queryParam("isFirstLogin", String.valueOf(dto.isFirstLogin()))
                .build().toUriString();

        response.sendRedirect(redirectUrl);
    }
}
