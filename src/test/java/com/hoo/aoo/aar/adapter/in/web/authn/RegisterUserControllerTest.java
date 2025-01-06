package com.hoo.aoo.aar.adapter.in.web.authn;

import com.hoo.aoo.aar.adapter.in.web.config.DocumentationTest;
import com.hoo.aoo.aar.application.port.in.MockRegisterUserUseCase;
import com.nimbusds.jose.shaded.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DocumentationTest
@Import({RegisterUserController.class, MockRegisterUserUseCase.class})
public class RegisterUserControllerTest {

    @Autowired
    MockMvc mockMvc;
    Gson gson = new Gson();

    @Test
    @DisplayName("회원가입 API")
    void testRegister() throws Exception {
        RegisterUserController.RegisterUserRequest dto = new RegisterUserController.RegisterUserRequest(true, true);
        String json = gson.toJson(dto);
        mockMvc.perform(post("/aar/authn/regist")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt().jwt(jwt -> jwt.claim("snsId", "1"))
                                .authorities(new SimpleGrantedAuthority("ROLE_TEMP_USER"))))
                .andExpect(status().is(200))
                .andDo(document("authn-regist",
                        requestFields(
                                fieldWithPath("recordAgreement").description("녹화물에 대한 2차 가공 동의여부입니다."),
                                fieldWithPath("personalInformationAgreement").description("사용자 개인정보 수집 및 활용에 대한 동의여부입니다.")
                        ),
                        responseFields(
                                fieldWithPath("nickname").description("회원가입한 사용자의 닉네임입니다."),
                                fieldWithPath("accessToken").description("회원가입한 사용자의 JWT 액세스 토큰입니다."))));
    }
}
