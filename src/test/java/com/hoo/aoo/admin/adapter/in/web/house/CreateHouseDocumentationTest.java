package com.hoo.aoo.admin.adapter.in.web.house;

import com.hoo.aoo.common.FixtureRepository;
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

    @Override
    protected String getBaseUrl() {
        return "api.archiveofongr.site";
    }

    @Test
    @DisplayName("하우스 생성 API")
    void testCreateHouse() throws Exception {

        String metadata = FixtureRepository.getMetadataJson();
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
                                partWithName("metadata").description("생성할 하우스의 정보를 포함하는 Json 형태의 문자열입니다."),
                                partWithName("house").description("생성할 하우스의 기본 이미지입니다."),
                                partWithName("border").description("생성할 하우스의 기본 외곽 이미지입니다."),
                                partWithName("room1").description("첫번째 방의 기본 이미지입니다."),
                                partWithName("room2").description("두번째 방의 기본 이미지입니다.")
                        ),
                        responseFields(
                                fieldWithPath("houseId").description("생성된 하우스의 아이디입니다.")
                        )
                ));
    }
}