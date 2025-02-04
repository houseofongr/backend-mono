package com.hoo.aoo.aar.adapter.in.web.home;

import com.hoo.aoo.common.adapter.in.web.config.AbstractControllerTest;
import com.hoo.aoo.common.adapter.in.web.config.DocumentationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetUserHomesControllerTest extends AbstractControllerTest{

    @Override
    protected String getBaseUrl() {
        return "api.archiveofongr.site";
    }

    @Test
    @Sql("GetUserHomesControllerTest.sql")
    @DisplayName("사용자 홈 리스트 조회 API")
    void getUserHomesAPI() throws Exception {
        mockMvc.perform(get("/aar/homes")
                        .with(jwt().jwt(jwt -> jwt.claim("userId", 10L))
                                .authorities(new SimpleGrantedAuthority("ROLE_USER"))
                        ))
                .andExpect(status().is(200))
                .andDo(document("aar-home-get-list",
                        responseFields(
                                fieldWithPath("homes[].id").description("홈의 아이디입니다."),
                                fieldWithPath("homes[].name").description("홈의 이름입니다.")
                        )
                ));
    }
}