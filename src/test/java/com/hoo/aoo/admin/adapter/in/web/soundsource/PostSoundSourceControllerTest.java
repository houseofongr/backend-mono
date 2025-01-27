package com.hoo.aoo.admin.adapter.in.web.soundsource;

import com.hoo.aoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostSoundSourceControllerTest extends AbstractControllerTest {

    @Override
    protected String getBaseUrl() {
        return "api.archiveofongr.site";
    }

    @Test
    @DisplayName("음원 생성 API 테스트")
    void testCreateSoundSourceAPI() throws Exception {

        //language=JSON
        String metadata = """
                {
                  "name" : "골골송",
                  "description" : "2025년 설이가 보내는 골골송",
                  "isActive" : true
                }
                """;

        MockPart metadataPart = new MockPart("metadata", metadata.getBytes());
        metadataPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        MockMultipartFile soundFile = new MockMultipartFile("soundFile", "golgolSong.mo3", "audio/mpeg", "golgolgolgolgolgolgolgol".getBytes());

        mockMvc.perform(multipart("/admin/items/{itemId}/sound-sources", 1L)
                        .part(metadataPart)
                        .file(soundFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is(201))
                .andDo(document("admin-soundsource-post",
                        pathParameters(
                                parameterWithName("itemId").description("해당 음원을 소유할 아이템 식별자입니다.")
                        ),
                        requestParts(
                                partWithName("metadata").description("생성할 음원 정보를 포함하는 Json 형태의 문자열입니다."),
                                partWithName("soundFile").description("저장할 음악 파일입니다. +" + "\n" + "지원 파일형식 : [mp3, wav]")
                        ),
                        responseFields(
                                fieldWithPath("soundSourceId").description("생성된 음원 파일 아이디입니다.")
                        )
                ));
    }
}