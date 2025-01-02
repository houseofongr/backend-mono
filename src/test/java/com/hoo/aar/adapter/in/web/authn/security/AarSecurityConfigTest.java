package com.hoo.aar.adapter.in.web.authn.security;

import com.hoo.aar.adapter.in.web.authn.security.handler.OAuth2SuccessHandler;
import com.hoo.aar.adapter.in.web.authn.security.service.MockOAuth2UserServiceDelegator;
import com.hoo.aar.application.port.in.RegisterUserUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AarSecurityConfigTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    RegisterUserUseCase registerUserUseCase;

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
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"recordAgreement\":true, \"personalInformationAgreement\":true}")
                        .with(jwt().jwt(jwt -> jwt.claim("snsId", "1"))
                                .authorities(new SimpleGrantedAuthority("ROLE_TEMP_USER"))))
                .andExpect(status().is(200));
    }

    @Test
    @DisplayName("등록되지 않은 URL 테스트")
    void testUnregisteredURL() throws Exception {
        mockMvc.perform(get("/aar/invalid_url").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));
    }

}