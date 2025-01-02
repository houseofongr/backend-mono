package com.hoo.aar.adapter.in.web.authn;

import com.hoo.aar.application.config.DocumentationTest;
import com.nimbusds.jose.shaded.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DocumentationTest
@Import(RegistController.class)
public class RegistControllerTest {

    @Autowired
    MockMvc mockMvc;
    Gson gson = new Gson();

    @Test
    @DisplayName("회원가입 API")
    void testRegist() throws Exception {
        RegistApiDto.Request dto = new RegistApiDto.Request("leaf", true,true);
        String json = gson.toJson(dto);
        mockMvc.perform(post("/aar/authn/regist")
                        .content(json)
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_TEMP_USER"))))
                .andExpect(status().is(200))
                .andDo(document("authn-regist",
                        requestFields(
                                fieldWithPath("nickName").description("해당 사용자가 사용할 닉네임입니다."),
                                fieldWithPath("recordAgreement").description("녹화물에 대한 2차 가공 동의여부입니다."),
                                fieldWithPath("personalInformationAgreement").description("사용자 개인정보 수집 및 활용에 대한 동의여부입니다.")
                        ),
                        responseFields(
                                fieldWithPath("username").description("회원가입한 사용자의 이름입니다."),
                                fieldWithPath("accessToken").description("회원가입한 사용자의 JWT 액세스 토큰입니다."))));
    }
}
