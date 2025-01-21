package com.hoo.aoo.admin.adapter.in.web.user;

import com.hoo.aoo.common.adapter.in.web.config.AbstractDocumentationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostHomeDocumentationTest extends AbstractDocumentationTest {

    @Override
    protected String getBaseUrl() {
        return "api.archiveofongr.site";
    }

    @Test
    @Sql("PostHomeDocumentationTest.sql")
    @DisplayName("Home 생성 테스트")
    void testCreateHome() throws Exception {

        String content = """
                {
                  "userId": 10,
                  "houseId": 20
                }
                """;

        mockMvc.perform(post("/admin/homes")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))
                .andDo(document("admin-home-post",
                        requestFields(
                                fieldWithPath("userId").description("홈을 생성할 사용자의 식별자입니다."),
                                fieldWithPath("houseId").description("홈의 템플릿으로 사용할 하우스의 식별자입니다.")
                        ),
                        responseFields(
                                fieldWithPath("createdHomeId").description("생성된 홈의 아이디입니다.")
                        )
                ));
    }
}