package com.hoo.aoo.admin.adapter.in.web.house;

import com.hoo.aoo.common.adapter.in.web.config.AbstractControllerTest;
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

class GetRoomControllerTest extends AbstractControllerTest {

    @Override
    protected String getBaseUrl() {
        return "api.archiveofongr.site";
    }

    @Test
    @Sql("GetRoomControllerTest.sql")
    @DisplayName("방 정보 조회 API")
    void testLoadRoomData() throws Exception {
        mockMvc.perform(get("/admin/houses/rooms/{roomId}/items", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-house-get-room",
                        pathParameters(
                                parameterWithName("roomId").description("조회할 방의 식별자입니다.")
                        ),
                        responseFields(
                                fieldWithPath("room.name").description("방의 이름입니다."),
                                fieldWithPath("room.width").description("방의 가로 길이입니다."),
                                fieldWithPath("room.height").description("방의 높이입니다."),
                                fieldWithPath("room.imageId").description("방의 이미지 식별자입니다."),

                                fieldWithPath("items[].id").description("아이템의 식별자입니다."),
                                fieldWithPath("items[].name").description("아이템의 이름입니다."),
                                fieldWithPath("items[].itemType").description("아이템의 타입입니다. +" + "\n" + "* 타입 : [CIRCLE, RECTANGLE, ELLIPSE]"),

                                fieldWithPath("items[].circleData").optional().description("원형 타입의 데이터입니다.").type("Object"),
                                fieldWithPath("items[].rectangleData").optional().description("직사각형 타입의 데이터입니다."),
                                fieldWithPath("items[].ellipseData").optional().description("타원형 타입의 데이터입니다.").type("Object"),

                                fieldWithPath("items[].circleData.x").optional().description("원형의 x좌표입니다.").type("Number"),
                                fieldWithPath("items[].circleData.y").optional().description("원형의 y좌표입니다.").type("Number"),
                                fieldWithPath("items[].circleData.radius").optional().description("원형의 반지름입니다.").type("Number"),

                                fieldWithPath("items[].rectangleData.x").optional().description("직사각형의 x좌표입니다."),
                                fieldWithPath("items[].rectangleData.y").optional().description("직사각형의 y좌표입니다."),
                                fieldWithPath("items[].rectangleData.width").optional().description("직사각형의 가로 너비입니다."),
                                fieldWithPath("items[].rectangleData.height").optional().description("직사각형의 세로 높이입니다."),
                                fieldWithPath("items[].rectangleData.rotation").optional().description("직사각형의 회전 각도입니다."),

                                fieldWithPath("items[].ellipseData.x").optional().description("타원형의 x좌표입니다.").type("Number"),
                                fieldWithPath("items[].ellipseData.y").optional().description("타원형의 y좌표입니다.").type("Number"),
                                fieldWithPath("items[].ellipseData.radiusX").optional().description("타원형의 x축 반지름입니다.").type("Number"),
                                fieldWithPath("items[].ellipseData.radiusY").optional().description("타원형의 y축 반지름입니다.").type("Number"),
                                fieldWithPath("items[].ellipseData.rotation").optional().description("타원형의 회전 각도입니다.").type("Number")
                        )
                ));
    }
}