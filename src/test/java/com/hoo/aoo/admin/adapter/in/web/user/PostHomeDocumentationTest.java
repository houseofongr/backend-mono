package com.hoo.aoo.admin.adapter.in.web.user;

import com.hoo.aoo.common.adapter.in.web.config.AbstractDocumentationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

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
    @DisplayName("Home 생성 테스트")
    void testCreateHome() throws Exception {

        String content = """
                {
                  "userId": 1,
                  "houseId": 1
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
                                fieldWithPath("message").description("생성 완료 메시지 : 0번 홈이 생성되었습니다.")
                        )
                ));
    }
}