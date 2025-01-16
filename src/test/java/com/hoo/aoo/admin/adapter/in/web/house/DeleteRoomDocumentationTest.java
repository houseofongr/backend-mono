package com.hoo.aoo.admin.adapter.in.web.house;

import com.hoo.aoo.common.adapter.in.web.config.AbstractDocumentationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeleteRoomDocumentationTest extends AbstractDocumentationTest {

    @Override
    protected String getBaseUrl() {
        return "api.archiveofongr.site";
    }

    @Test
    @DisplayName("룸 삭제 API")
    void testDeleteRoom() throws Exception {
        mockMvc.perform(delete("/admin/houses/{houseId}/rooms/{roomName}", 1L, "거실")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-house-room-delete",
                        pathParameters(
                                parameterWithName("houseId").description("삭제할 방이 소속된 하우스의 식별자입니다."),
                                parameterWithName("roomName").description("삭제할 방의 이름입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("삭제 완료 메시지 : 0번 하우스 00 룸이 삭제되었습니다.")
                        )
                ));
    }
}