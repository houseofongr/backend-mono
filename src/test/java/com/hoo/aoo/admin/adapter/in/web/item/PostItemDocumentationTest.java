package com.hoo.aoo.admin.adapter.in.web.item;

import com.hoo.aoo.common.FixtureRepository;
import com.hoo.aoo.common.adapter.in.web.config.AbstractDocumentationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostItemDocumentationTest extends AbstractDocumentationTest {

    @Override
    protected String getBaseUrl() {
        return "api.archiveofongr.site";
    }

    @Test
    @Sql("PostItemDocumentationTest.sql")
    @DisplayName("아이템 생성 API")
    void testCreateItemAPI() throws Exception {

        String metadata = FixtureRepository.getCreateItemMetadataJson();
        MockPart metadataPart = new MockPart("metadata", metadata.getBytes());
        metadataPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        MockMultipartFile record1 = new MockMultipartFile("record1", "test.mp3", "audio/mpeg", "<<mpeg data 1>>".getBytes());
        MockMultipartFile record2 = new MockMultipartFile("record2", "test2.mp3", "audio/mpeg", "<<mpeg data 2>>".getBytes());
        MockMultipartFile record3 = new MockMultipartFile("record3", "test3.mp3", "audio/mpeg", "<<mpeg data 2>>".getBytes());

        mockMvc.perform(multipart("/admin/users/{userId}/homes/{homeId}/rooms/{roomId}/items", 10L, 1L, 1L)
                        .file(record1).file(record2).file(record3)
                        .part(metadataPart)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is(201))
                .andDo(document("admin-item-post",
                        pathParameters(
                                parameterWithName("userId").description("생성할 아이템을 소유한 사용자의 식별자입니다. 파일을 개인화하는데 사용됩니다."),
                                parameterWithName("homeId").description("생성할 아이템을 보유한 홈의 식별자입니다."),
                                parameterWithName("roomId").description("생성할 아이템을 포함할 방의 식별자입니다.")
                        ),
                        requestParts(
                                partWithName("metadata").description("생성할 아이템의 정보를 포함하는 Json 문자열입니다."),
                                partWithName("record1").description("생성할 아이템 1의 음원입니다."),
                                partWithName("record2").description("생성할 아이템 2의 음원입니다."),
                                partWithName("record3").description("생성할 아이템 3의 음원입니다.")
                        ),
                        responseFields(
                                fieldWithPath("createdItemIds[]").description("생성된 아이템의 식별자 리스트입니다.")
                        )
                ));
    }
}