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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import({AarSecurityConfig.class, OAuth2SuccessHandler.class, MockOAuth2UserServiceDelegator.class})
class AarSecurityConfigTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("OAuth2 Login 테스트")
    void testOAuth2Login() throws Exception {
        mockMvc.perform(get("/aar/authn/login/{provider}", "kakao")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(302));
    }

    @Test
    @DisplayName("Authorization Entry Point 테스트")
    void testUnauthorized() throws Exception {
        mockMvc.perform(get("/aar/invalid_url")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));
    }

}