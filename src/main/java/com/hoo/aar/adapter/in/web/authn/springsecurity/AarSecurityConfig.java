package com.hoo.aar.adapter.in.web.authn.springsecurity;

import com.hoo.aar.adapter.in.web.authn.springsecurity.handler.OAuth2SuccessHandler;
import com.hoo.aar.adapter.in.web.authn.springsecurity.service.OAuth2UserServiceDelegator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class AarSecurityConfig {

    @Bean
    public SecurityFilterChain aarFilterChain(HttpSecurity http, OAuth2UserServiceDelegator userService, OAuth2SuccessHandler oAuth2SuccessHandler) throws Exception {
        return http
                .securityMatcher("/aar/**")
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(authorization ->
                                authorization.baseUri("/aar/authn/login/**"))
                        .redirectionEndpoint(redirection ->
                                redirection.baseUri("/aar/authn/code/**"))
                        .userInfoEndpoint(userInfo ->
                                userInfo.userService(userService))
                        .successHandler(oAuth2SuccessHandler))

                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers("/aar/authn/login/**").permitAll()
                                .requestMatchers("/aar/authn/kakao/callback").permitAll()
                                .anyRequest().authenticated())

                .exceptionHandling(handler ->
                        handler.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))

                .build();
    }


}
