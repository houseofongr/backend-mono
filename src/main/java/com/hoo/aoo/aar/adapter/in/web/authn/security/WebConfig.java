package com.hoo.aoo.aar.adapter.in.web.authn.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.annotation.AnnotationTemplateExpressionDefaults;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;

@Configuration
@EnableWebSecurity
public class WebConfig {

    @Bean
    public AnnotationTemplateExpressionDefaults templateDefaults() {
        return new AnnotationTemplateExpressionDefaults();
    }

    @Bean
    public HandlerMethodArgumentResolver handlerMethodArgumentResolver() {
        HandlerMethodArgumentResolverComposite argumentResolverComposite = new HandlerMethodArgumentResolverComposite();
        argumentResolverComposite.addResolver(new AuthenticationPrincipalArgumentResolver());

        return argumentResolverComposite;
    }
}
