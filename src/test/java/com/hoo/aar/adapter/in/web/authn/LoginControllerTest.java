package com.hoo.aar.adapter.in.web.authn;

import com.hoo.aar.application.config.DocumentationTest;
import com.hoo.aar.application.config.MockDocumentationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockDocumentationTest
@Import(MockOAuth2Controller.class)
public class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("카카오 로그인 API")
    void testKakaoLogin() throws Exception {
        mockMvc.perform(get("/aar/authn/login/{provider}", "kakao")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(302))
                .andDo(document("authn-kakao",
                        pathParameters(parameterWithName("provider").description("로그인을 시도할 서드파티 인증 기관명입니다. +" + "\n" + "[kakao, naver, google, apple]")),
                        responseFields(fieldWithPath("username").description("로그인한 사용자의 이름입니다."),
                                fieldWithPath("accessToken").description("로그인한 사용자의 JWT 액세스 토큰입니다."),
                                fieldWithPath("provider").description("로그인을 제공한 서드파티 인증 기관명입니다. +" +"\n" + "[kakao, naver, google, apple]"),
                                fieldWithPath("isFirstLogin").description("해당 사용자가 처음 로그인했는지 여부입니다."))));
    }
}
