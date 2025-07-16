package com.hoo.main.adapter.in.web.authn.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class AarSecurityConfig {

    @Order(3)
    @Bean
    public SecurityFilterChain aarFilterChain(HttpSecurity http, OAuth2UserServiceDelegator userService, OAuth2SuccessHandler oAuth2SuccessHandler, OAuth2FailureHandler failureHandler, JwtDecoder jwtDecoder) throws Exception {
        return http
                .securityMatcher("/**")
                .csrf(CsrfConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(authorization ->
                                authorization.baseUri("/authn/login/**"))
                        .redirectionEndpoint(redirection ->
                                redirection.baseUri("/authn/code/**"))
                        .userInfoEndpoint(userInfo ->
                                userInfo.userService(userService))
                        .successHandler(oAuth2SuccessHandler)
                        .failureHandler(failureHandler))

                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.decoder(jwtDecoder)))

                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(GET,
                                        "/homes/**")
                                .hasRole("USER")

                                .requestMatchers(POST,
                                        "/authn/regist")
                                .hasRole("TEMP_USER")

                                .requestMatchers(GET,
                                        "/authn/login/**",
                                        "/authn/code/**",
                                        "/authn/kakao/callback",
                                        "/users/nickname/**",
                                        "/error-codes",
                                        "/universes/**")
                                .permitAll()

                                .requestMatchers(POST,
                                        "/users/business",
                                        "/authn/email-code/**",
                                        "/authn/business/login")
                                .permitAll()

                                .anyRequest().authenticated())

                .exceptionHandling(handler ->
                        handler.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))

                .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("role");
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000", "https://dev.archiveofongr.site"));
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setExposedHeaders((List.of("Authorization", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

}
