package com.hoo.aoo.admin.adapter.in.web.house;

import com.hoo.aoo.common.adapter.in.web.config.AbstractDocumentationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class MetadataDocumentationTest extends AbstractDocumentationTest {

    @Override
    protected String getBaseUrl() {
        return "api.archiveofongr.site";
    }

    @Test
    @DisplayName("Metadata 생성 테스트")
    void testCreateMetadata() throws Exception {
        mockMvc.perform(get("/mock/admin/house/metadata")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(document("admin-house-create-metadata",
                        responseFields(
                                fieldWithPath("house.title").description("하우스의 제목입니다."),
                                fieldWithPath("house.author").description("하우스의 작가입니다."),
                                fieldWithPath("house.description").description("하우스에 대한 설명입니다."),
                                fieldWithPath("house.houseFormName").description("기본 이미지로 사용할 이미지 파일의 Form 태그 name 속성값입니다."),
                                fieldWithPath("house.borderFormName").description("테두리 이미지로 사용할 이미지 파일의 Form 태그 name 속성값입니다."),
                                fieldWithPath("house.width").description("하우스의 가로 길이입니다."),
                                fieldWithPath("house.height").description("하우스의 높이입니다."),
                                fieldWithPath("rooms[].formName").description("방의 이미지로 사용할 이미지 파일의 Form 태그 name 속성값입니다."),
                                fieldWithPath("rooms[].name").description("방의 이름입니다."),
                                fieldWithPath("rooms[].x").description("방의 시작점(X좌표)입니다."),
                                fieldWithPath("rooms[].y").description("방의 시작점(Y좌표)입니다."),
                                fieldWithPath("rooms[].z").description("방의 시작점(Z좌표)입니다."),
                                fieldWithPath("rooms[].width").description("방의 가로 길이입니다."),
                                fieldWithPath("rooms[].height").description("방의 높이입니다.")
                        )));

    }

}
