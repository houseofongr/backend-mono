package com.hoo.aar.adapter.in.web.authn;

import com.hoo.aar.application.config.MockDocumentationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@MockDocumentationTest
@Import(MockOAuth2Controller2.class)
public class LoginControllerTest2 {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("SNS 로그인 API 2")
    void testSnsLogin() throws Exception {
        mockMvc.perform(get("/aar/authn/login/{provider}/v2", "kakao")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(document("authn-kakao-2",
                        pathParameters(parameterWithName("provider").description("로그인을 시도할 서드파티 인증 기관명입니다. +" + "\n" + "[kakao, naver, google, apple]")),
                        responseFields(fieldWithPath("username").description("로그인한 사용자의 이름입니다."),
                                fieldWithPath("accessToken").description("로그인한 사용자의 JWT 액세스 토큰입니다."),
                                fieldWithPath("provider").description("로그인을 제공한 서드파티 인증 기관명입니다. +" + "\n" + "[kakao, naver, google, apple]"),
                                fieldWithPath("isFirstLogin").description("해당 사용자가 처음 로그인했는지 여부입니다."))));
    }

}
