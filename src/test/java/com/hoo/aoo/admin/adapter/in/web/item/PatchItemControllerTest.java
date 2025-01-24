package com.hoo.aoo.admin.adapter.in.web.item;

import com.hoo.aoo.admin.application.port.in.item.UpdateItemCommand;
import com.hoo.aoo.admin.domain.item.ItemType;
import com.hoo.aoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.hoo.aoo.common.util.GsonUtil.gson;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PatchItemControllerTest extends AbstractControllerTest {

    @Override
    protected String getBaseUrl() {
        return "api.archiveofongr.site";
    }

    @Test
    @DisplayName("아이템 수정 API")
    void testPatchItemAPI() throws Exception {

        UpdateItemCommand command = new UpdateItemCommand("고양이", ItemType.RECTANGLE, null,
                new UpdateItemCommand.RectangleData(300f, 300f, 20f, 20f, 10f), null);

        mockMvc.perform(patch("/admin/items/{itemId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(command)))
                .andExpect(status().is(200))
                .andDo(document("admin-item-patch",
                        pathParameters(
                                parameterWithName("itemId").description("수정할 아이템의 식별자입니다.")
                        ),
                        requestFields(
                                fieldWithPath("name").optional().description("수정할 아이템의 이름입니다."),
                                fieldWithPath("itemType").optional().description("수정할 아이템의 타입입니다. +" + "\n" + "* 타입 : [CIRCLE, RECTANGLE, ELLIPSE]"),

                                fieldWithPath("circleData").optional().description("원형 타입의 데이터입니다. +" + "\n" + "* 해당 타입으로 수정하지 않으면 비워서(null) 전송합니다.").type(UpdateItemCommand.CircleData.class),
                                fieldWithPath("rectangleData").optional().description("직사각형 타입의 데이터입니다. +" + "\n" + "* 해당 타입으로 수정하지 않으면 비워서(null) 전송합니다."),
                                fieldWithPath("ellipseData").optional().description("타원형 타입의 데이터입니다. +" + "\n" + "* 해당 타입으로 수정하지 않으면 비워서(null) 전송합니다.").type(UpdateItemCommand.EllipseData.class),

                                fieldWithPath("circleData.x").optional().description("원형의 x좌표입니다.").type(Float.class),
                                fieldWithPath("circleData.y").optional().description("원형의 y좌표입니다.").type(Float.class),
                                fieldWithPath("circleData.radius").optional().description("원형의 반지름입니다.").type(Float.class),

                                fieldWithPath("rectangleData.x").optional().description("직사각형의 x좌표입니다."),
                                fieldWithPath("rectangleData.y").optional().description("직사각형의 y좌표입니다."),
                                fieldWithPath("rectangleData.width").optional().description("직사각형의 가로 너비입니다."),
                                fieldWithPath("rectangleData.height").optional().description("직사각형의 세로 높이입니다."),
                                fieldWithPath("rectangleData.rotation").optional().description("직사각형의 회전 각도입니다."),

                                fieldWithPath("ellipseData.x").optional().description("타원형의 x좌표입니다.").type(Float.class),
                                fieldWithPath("ellipseData.y").optional().description("타원형의 y좌표입니다.").type(Float.class),
                                fieldWithPath("ellipseData.radiusX").optional().description("타원형의 x축 반지름입니다.").type(Float.class),
                                fieldWithPath("ellipseData.radiusY").optional().description("타원형의 y축 반지름입니다.").type(Float.class),
                                fieldWithPath("ellipseData.rotation").optional().description("타원형의 회전 각도입니다.").type(Float.class)
                        ),
                        responseFields(
                                fieldWithPath("message").description("수정 완료 메시지 : 0번 아이템의 정보가 수정되었습니다.")
                        )
                ));
    }
}