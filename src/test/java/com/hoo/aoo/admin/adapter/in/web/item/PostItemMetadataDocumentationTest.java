package com.hoo.aoo.admin.adapter.in.web.item;

import com.hoo.aoo.common.adapter.in.web.config.AbstractDocumentationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class PostItemMetadataDocumentationTest extends AbstractDocumentationTest {

    @Override
    protected String getBaseUrl() {
        return "api.archiveofongr.site";
    }

    @Test
    @DisplayName("Metadata 생성 테스트")
    void testCreateMetadata() throws Exception {
        mockMvc.perform(get("/mock/admin/create-item-metadata")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(document("admin-create-item-metadata",
                        responseFields(
                                fieldWithPath("items[].form").description("아이템의 음원으로 사용할 오디오 파일의 Form 태그 name 속성값입니다. +" + "\n" + "지원 파일형식 : [mp3, wav]"),
                                fieldWithPath("items[].name").description("생성할 아이템의 이름입니다."),
                                fieldWithPath("items[].itemType").description("생성할 아이템의 타입입니다. +" + "\n" + "[CIRCLE, RECTANGLE, ELLIPSE]"),

                                fieldWithPath("items[].circleData").optional().description("원형 타입의 데이터입니다. +" + "\n" + "* 해당 타입으로 생성하지 않으면 비워서(null) 전송합니다."),
                                fieldWithPath("items[].rectangleData").optional().description("직사각형 타입의 데이터입니다. +" + "\n" + "* 해당 타입으로 생성하지 않으면 비워서(null) 전송합니다."),
                                fieldWithPath("items[].ellipseData").optional().description("타원형 타입의 데이터입니다. +" + "\n" + "* 해당 타입으로 생성하지 않으면 비워서(null) 전송합니다."),

                                fieldWithPath("items[].circleData.x").optional().description("원형의 x좌표입니다."),
                                fieldWithPath("items[].circleData.y").optional().description("원형의 y좌표입니다."),
                                fieldWithPath("items[].circleData.radius").optional().description("원형의 반지름입니다."),

                                fieldWithPath("items[].rectangleData.x").optional().description("직사각형의 x좌표입니다."),
                                fieldWithPath("items[].rectangleData.y").optional().description("직사각형의 y좌표입니다."),
                                fieldWithPath("items[].rectangleData.width").optional().description("직사각형의 가로 너비입니다."),
                                fieldWithPath("items[].rectangleData.height").optional().description("직사각형의 세로 높이입니다."),
                                fieldWithPath("items[].rectangleData.angle").optional().description("직사각형의 회전 각도입니다."),

                                fieldWithPath("items[].ellipseData.x").optional().description("타원형의 x좌표입니다."),
                                fieldWithPath("items[].ellipseData.y").optional().description("타원형의 y좌표입니다."),
                                fieldWithPath("items[].ellipseData.width").optional().description("타원형의 가로 너비입니다."),
                                fieldWithPath("items[].ellipseData.height").optional().description("타원형의 세로 높이입니다."),
                                fieldWithPath("items[].ellipseData.angle").optional().description("타원형의 회전 각도입니다.")
                        )));
    }

}
