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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetRoomDocumentationTest extends AbstractDocumentationTest {

    @Override
    protected String getBaseUrl() {
        return "api.archiveofongr.site";
    }

    @Test
    @Sql("GetRoomDocumentationTest.sql")
    @DisplayName("방 정보 조회 API")
    void testLoadRoomData() throws Exception {
        mockMvc.perform(get("/admin/houses/{houseId}/rooms/{roomId}", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-house-room-load",
                        pathParameters(
                                parameterWithName("houseId").description("조회할 하우스의 식별자입니다."),
                                parameterWithName("roomId").description("조회할 방의 식별자입니다.")
                        ),
                        responseFields(
                                fieldWithPath("room.name").description("방의 이름입니다."),
                                fieldWithPath("room.width").description("방의 가로 길이입니다."),
                                fieldWithPath("room.height").description("방의 높이입니다."),
                                fieldWithPath("room.imageId").description("방의 이미지 식별자입니다.")
                        )
                ));
    }
}