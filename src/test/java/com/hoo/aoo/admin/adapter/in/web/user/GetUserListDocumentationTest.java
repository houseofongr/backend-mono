package com.hoo.aoo.admin.adapter.in.web.user;

import com.hoo.aoo.common.adapter.in.web.config.AbstractDocumentationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetUserListDocumentationTest extends AbstractDocumentationTest {

    @Override
    protected String getBaseUrl() {
        return "api.archiveofongr.site";
    }

    @Test
    @DisplayName("사용자 리스트 조회 API")
    void testGetUserList() throws Exception {
        mockMvc.perform(get("/admin/users"))
                .andExpect(status().is(200))
                .andDo(document("admin-user-get-list",
                        responseFields(
                                fieldWithPath("users[].id").description("사용자의 식별자입니다."),
                                fieldWithPath("users[].name").description("사용자의 이름입니다."),
                                fieldWithPath("users[].email").description("사용자의 이메일 주소입니다."),
                                fieldWithPath("users[].phoneNumber").description("사용자의 전화번호입니다."),
                                fieldWithPath("users[].registeredDate").description("사용자의 등록일입니다.")
                        )
                        ));
    }
}