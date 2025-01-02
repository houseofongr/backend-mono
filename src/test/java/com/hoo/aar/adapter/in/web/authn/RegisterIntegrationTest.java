package com.hoo.aar.adapter.in.web.authn;

import com.hoo.aar.adapter.in.web.config.IntegrationTest;
import com.hoo.aar.adapter.out.persistence.repository.UserJpaRepository;
import com.hoo.aar.application.port.in.RegisterUserCommand;
import com.nimbusds.jose.shaded.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class RegisterIntegrationTest {

    MockMvc mockMvc;

    Gson gson = new Gson();

    @Autowired
    JwtDecoder jwtDecoder;

    @Autowired
    UserJpaRepository repository;

    @BeforeEach
    void init(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .apply(springSecurity())
                .build();
    }

    @Test
    @Sql("RegisterIntegrationTest.sql")
    @DisplayName("회원가입 통합테스트")
    void testRegister() throws Exception {
        // given
        RegisterUserController.RegisterUserRequest request = new RegisterUserController.RegisterUserRequest(true, true);

        // when : 닉네임, 토큰 전송
        String responseBody = mockMvc.perform(post("/aar/authn/regist").content(gson.toJson(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt().jwt(jwt -> jwt.claim("snsId", 1L))
                                .authorities(new SimpleGrantedAuthority("ROLE_TEMP_USER"))))
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString();
        RegisterUserCommand.Out res = gson.fromJson(responseBody, RegisterUserCommand.Out.class);

        // then : 사용자 닉네임 확인
        assertThat(res.nickname()).isEqualTo("leaf");

        // then : 토큰 권한 확인
        Jwt jwt = jwtDecoder.decode(res.accessToken());
        assertThat((String) jwt.getClaim("role")).isEqualTo("USER");

        // then : 사용자 정보 저장여부 확인
        assertThat(repository.findByNickname("leaf")).isNotEmpty();
    }
}
