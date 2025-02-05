package com.hoo.aoo.aar.adapter.in.web.user;

import com.hoo.aoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PatchMyInfoControllerTest extends AbstractControllerTest {

    @Override
    protected String getBaseUrl() {
        return "api.archiveofongr.site";
    }

    @Test
    @DisplayName("본인정보 수정 API 테스트")
    void testPatchMyInfoAPI() throws Exception {

        //language=JSON
        String content = """
                {
                  "nickname" : "leaf2"
                }
                """;
        mockMvc.perform(patch("/aar/users/me")
                        .content(content)
                        .with(jwt().jwt(jwt -> jwt.claim("userId", 10L))
                                .authorities(new SimpleGrantedAuthority("ROLE_USER"))
                        ))
                .andExpect(status().is(200))
                .andDo(document("aar-user-patch-me",
                        requestFields(
                                fieldWithPath("nickname").description("수정할 닉네임입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("수정 완료 메시지 : 본인정보가 수정되었습니다.")
                        )
                ));
    }
}