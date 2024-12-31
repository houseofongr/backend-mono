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

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Value("${aar.authn.frontend-redirect-uri}")
    private String redirectUri;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();

        String redirectUrl = UriComponentsBuilder.fromUriString(redirectUri)
                .queryParams(Login.Response.getQueryParams(user.getAttributes()))
                .build().toUriString();

        response.sendRedirect(redirectUrl);
    }
}
