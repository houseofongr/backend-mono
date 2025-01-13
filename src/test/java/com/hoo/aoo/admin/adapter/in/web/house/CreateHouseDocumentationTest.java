package com.hoo.aoo.admin.adapter.in.web.house;

import com.hoo.aoo.common.adapter.in.web.config.AbstractDocumentationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CreateHouseDocumentationTest extends AbstractDocumentationTest {

    private static String getMetadata() {
        String metadata = """
                {
                  "house": {
                    "title": "cozy house",
                    "author": "leaf",
                    "description": "this is cozy house.",
                    "width": 5000,
                    "height": 5000,
                    "houseFormName": "house",
                    "borderFormName": "border"
                  },
                  "rooms": [
                    {
                      "formName": "room1",
                      "name": "거실",
                      "x": 123,
                      "y": 456,
                      "z": 1,
                      "width": 100,
                      "height": 200
                    },
                    {
                      "formName": "room2",
                      "name": "주방",
                      "x": 234,
                      "y": 345,
                      "z": 2,
                      "width": 200,
                      "height": 200
                    }
                  ]
                }
                """;

        return metadata;
    }

    @Override
    protected String getBaseUrl() {
        return "api.archiveofongr.site";
    }

    @Test
    @DisplayName("집 생성 API")
    void testCreateHouse() throws Exception {

        String metadata = getMetadata();
        MockPart metadataPart = new MockPart("metadata", metadata.getBytes());
        metadataPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        MockMultipartFile houseImage = new MockMultipartFile("house", "image.png", "image/png", "<<png data 1>>".getBytes());
        MockMultipartFile borderImage = new MockMultipartFile("border", "image2.png", "image/png", "<<png data 2>>".getBytes());
        MockMultipartFile roomImage1 = new MockMultipartFile("room1", "image3.png", "image/png", "<<png data 3>>".getBytes());
        MockMultipartFile roomImage2 = new MockMultipartFile("room2", "image4.png", "image/png", "<<png data 4>>".getBytes());

        mockMvc.perform(multipart("/admin/houses")
                        .file(houseImage).file(borderImage).file(roomImage1).file(roomImage2)
                        .part(metadataPart)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(201))
                .andDo(document("admin-house-create",
                        requestParts(
                                partWithName("metadata").description("생성할 집의 정보를 포함하는 Json 형태의 문자열입니다."),
                                partWithName("house").description("생성할 집의 기본 이미지입니다."),
                                partWithName("border").description("생성할 집의 기본 외곽 이미지입니다."),
                                partWithName("room1").description("첫번째 방의 기본 이미지입니다."),
                                partWithName("room2").description("두번째 방의 기본 이미지입니다.")
                        ),
                        responseFields(
                                fieldWithPath("house.id").description("생성된 집의 아이디입니다."),
                                fieldWithPath("house.title").description("생성된 방의 제목입니다."),
                                fieldWithPath("house.author").description("생성한 작가의 이름입니다."),
                                fieldWithPath("house.roomCnt").description("생성된 방의 개수입니다."),
                                fieldWithPath("house.imageFileId").description("생성된 방의 기본 이미지입니다."),
                                fieldWithPath("house.borderImageFileId").description("생성된 방의 기본 외곽 이미지입니다."),
                                fieldWithPath("rooms[].name").description("생성된 방의 이름입니다."),
                                fieldWithPath("rooms[].imageFileId").description("생성된 방의 이미지 아이디입니다."))
                ));
    }

}