package com.hoo.aar.adapter.in.web.config;

import com.hoo.aar.adapter.in.web.authn.config.WebConfig;
import com.hoo.aar.adapter.in.web.authn.security.AarSecurityConfig;
import com.hoo.aar.adapter.in.web.authn.security.handler.OAuth2SuccessHandler;
import com.hoo.aar.adapter.in.web.authn.security.jwt.JwtConfig;
import com.hoo.aar.adapter.in.web.authn.security.service.MockOAuth2UserServiceDelegator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(RestDocumentationExtension.class)
@WebAppConfiguration
@WebMvcTest
@AutoConfigureRestDocs
@ContextConfiguration(classes = {AarSecurityConfig.class, OAuth2SuccessHandler.class, MockOAuth2UserServiceDelegator.class, DocumentationConfig.class, JwtConfig.class, WebConfig.class})
public @interface DocumentationTest {
}
