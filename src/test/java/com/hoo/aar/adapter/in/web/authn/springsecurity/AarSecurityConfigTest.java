package com.hoo.aar.adapter.in.web.authn.springsecurity;

import com.hoo.aar.adapter.in.web.authn.springsecurity.handler.OAuth2SuccessHandler;
import com.hoo.aar.adapter.in.web.authn.springsecurity.service.MockOAuth2UserServiceDelegator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import({AarSecurityConfig.class, OAuth2SuccessHandler.class, MockOAuth2UserServiceDelegator.class})
class AarSecurityConfigTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("전체 공개 URL 테스트")
    void testPublicURL() throws Exception {
        mockMvc.perform(get("/aar/authn/login/{provider}", "kakao").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(302));
        mockMvc.perform(get("/aar/authn/login/kakao/callback", "kakao").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(500));
    }

    @Test
    @DisplayName("TEMP_USER 권한 URL 테스트")
    void testTempUserURL() throws Exception {
        mockMvc.perform(post("/aar/authn/regist").accept(MediaType.APPLICATION_JSON)
                        .with(user("temp_user").roles("TEMP_USER")))
                .andExpect(status().is(200));
    }

    @Test
    @DisplayName("등록되지 않은 URL 테스트")
    void testUnregisteredURL() throws Exception {
        mockMvc.perform(get("/aar/invalid_url").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));
    }

}