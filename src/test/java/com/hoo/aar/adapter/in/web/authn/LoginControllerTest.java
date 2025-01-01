package com.hoo.aar.adapter.in.web.authn;

import com.hoo.aar.application.config.MockDocumentationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockDocumentationTest
@Import(MockOAuth2Controller.class)
public class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("SNS 로그인 API")
    void testSnsLogin() throws Exception {
        mockMvc.perform(get("/aar/authn/login/{provider}", "kakao")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(302))
                .andDo(document("authn-kakao",
                        pathParameters(parameterWithName("provider").description("로그인을 시도할 서드파티 인증 기관명입니다. +" + "\n" + "[kakao, naver, google, apple]")),
                        responseHeaders(headerWithName("Location").description("로그인 이후 이동하는 경로와 쿼리 파라미터입니다."))));
    }
}
