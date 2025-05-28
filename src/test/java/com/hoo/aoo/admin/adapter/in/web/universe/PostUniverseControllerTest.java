package com.hoo.aoo.admin.adapter.in.web.universe;

import com.hoo.aoo.common.adapter.in.web.config.AbstractControllerTest;
import com.hoo.aoo.file.adapter.out.persistence.entity.FileJpaEntity;
import com.hoo.aoo.file.adapter.out.persistence.repository.FileJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostUniverseControllerTest extends AbstractControllerTest {

    @Autowired
    FileJpaRepository fileJpaRepository;
    //language=JSON
    String metadata = """
            {
              "title": "우주",
              "description": "유니버스는 우주입니다.",
              "authorId": 1,
              "category": "GOVERNMENT_AND_PUBLIC_INSTITUTION",
              "publicStatus": "PUBLIC",
              "tags": [
                "우주", "행성", "지구", "별"
              ]
            }
            """;

    @Override
    public boolean useSpringSecurity() {
        return false;
    }

    @Test
    @DisplayName("유니버스 생성 API")
    @Sql("PostUniverseControllerTest.sql")
    void testCreateUniverse() throws Exception {

        MockPart metadataPart = new MockPart("metadata", metadata.getBytes());
        metadataPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        MockMultipartFile innerImage = new MockMultipartFile("innerImage", "universe_inner_image.png", "image/png", "image file".getBytes());
        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "universe_thumb.png", "image/png", "universe file".getBytes());
        MockMultipartFile thumbMusic = new MockMultipartFile("thumbMusic", "universe_music.mp3", "audio/mpeg", "music file".getBytes());

        mockMvc.perform(multipart("/admin/universes")
                        .file(thumbnail).file(thumbMusic).file(innerImage)
                        .part(metadataPart)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(201))
                .andDo(document("admin-universe-post",
                        requestParts(
                                partWithName("metadata").description("생성할 유니버스의 정보를 포함하는 Json 형태의 문자열입니다."),
                                partWithName("thumbnail").description("생성할 유니버스의 썸네일 이미지입니다."),
                                partWithName("thumbMusic").description("생성할 유니버스의 썸뮤직 오디오입니다."),
                                partWithName("innerImage").description("생성할 유니버스의 내부이미지입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("생성 완료 메시지 : 0번 유니버스가 생성되었습니다.")
                        )
                ));

        List<FileJpaEntity> fileInDB = fileJpaRepository.findAll();
        assertThat(fileInDB).hasSize(3);
        assertThat(fileInDB)
                .anySatisfy(fileJpaEntity -> assertThat(fileJpaEntity.getRealFileName()).isEqualTo("universe_inner_image.png"))
                .anySatisfy(fileJpaEntity -> assertThat(fileJpaEntity.getRealFileName()).isEqualTo("universe_thumb.png"))
                .anySatisfy(fileJpaEntity -> assertThat(fileJpaEntity.getRealFileName()).isEqualTo("universe_music.mp3"));
    }

}