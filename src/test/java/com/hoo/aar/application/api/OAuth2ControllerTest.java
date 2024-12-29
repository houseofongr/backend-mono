package com.hoo.aar.application.api;

import com.hoo.aar.application.config.DocumentationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DocumentationTest
@Import(MockOAuth2Controller.class)
public class OAuth2ControllerTest {

    @Autowired
    MockMvc securityMockMvc;

    @Test
    @DisplayName("카카오 로그인 API")
    void testKakaoLogin() throws Exception {
        securityMockMvc.perform(get("/authn/kakao").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andDo(document("authn-kakao",
                        responseFields(fieldWithPath("username").description("로그인한 사용자의 이름입니다."),
                                fieldWithPath("accessToken").description("로그인한 사용자의 JWT 액세스 토큰입니다."),
                                fieldWithPath("provider").description("로그인을 제공한 서드파티 인증 기관명입니다."))));
    }
}
