package com.hoo.aoo.admin.adapter.in.web.house;

import com.hoo.aoo.common.adapter.in.web.config.AbstractDocumentationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PatchRoomInfoDocumentationTest extends AbstractDocumentationTest {

    @Override
    protected String getBaseUrl() {
        return "api.archiveofongr.site";
    }

    @Test
    @Sql("PatchRoomInfoDocumentationTest.sql")
    @DisplayName("룸 정보 수정 API")
    void testRoomInfoUpdate() throws Exception {

        String requestBody = """
                [
                    {
                      "houseDetail" : "1",
                      "newName" : "욕실"
                    }, {
                      "houseDetail" : "2",
                      "newName" : "베란다"
                    }
                ]
                """;

        mockMvc.perform(patch("/admin/houses/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-house-patch-room",
                        requestFields(
                                fieldWithPath("[].houseDetail").description("수정할 룸의 식별자입니다."),
                                fieldWithPath("[].newName").description("수정할 이름입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("수정 완료 메시지 : 0개 룸의 정보 수정이 완료되었습니다.")
                        )
                ));
    }
}