package com.hoo.aoo.admin.adapter.in.web.universe;

import com.hoo.aoo.common.adapter.in.web.config.AbstractControllerTest;
import com.hoo.aoo.file.adapter.out.persistence.entity.FileJpaEntity;
import com.hoo.aoo.file.adapter.out.persistence.repository.FileJpaRepository;
import com.hoo.aoo.file.domain.FileF;
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

@Sql("PostUpdateUniverseControllerTest.sql")
class PostUpdateUniverseControllerTest extends AbstractControllerTest {

    @Autowired
    FileJpaRepository fileJpaRepository;
    //language=JSON
    String metadata = """
            {
              "targetId": 1,
              "title": "오르트구름",
              "description": "오르트구름은 태양계 최외곽에 위치하고 있습니다.",
              "category": "LIFE",
              "publicStatus": "PRIVATE",
              "tags": [
                "오르트구름", "태양계", "윤하", "별"
              ]
            }
            """;

    @Override
    public boolean useSpringSecurity() {
        return false;
    }

    @Test
    @DisplayName("유니버스 수정 API")
    void testUniverseUpdateAPI() throws Exception {
        saveFile(FileF.IMAGE_FILE_1.get(tempDir.toString()));
        saveFile(FileF.IMAGE_FILE_2.get(tempDir.toString()));

        MockPart metadataPart = new MockPart("metadata", metadata.getBytes());
        metadataPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "new_universe_thumb.png", "image/png", "universe file".getBytes());
        MockMultipartFile thumbMusic = new MockMultipartFile("thumbMusic", "new_universe_music.mp3", "audio/mpeg", "music file".getBytes());

        mockMvc.perform(multipart("/admin/universes/update")
                        .file(thumbnail).file(thumbMusic)
                        .part(metadataPart)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-universe-post-update",
                        requestParts(
                                partWithName("metadata").description("생성할 유니버스의 정보를 포함하는 Json 형태의 문자열입니다."),
                                partWithName("thumbnail").description("생성할 유니버스의 썸네일 이미지입니다."),
                                partWithName("thumbMusic").description("생성할 유니버스의 썸뮤직 오디오입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("생성 완료 메시지 : 0번 유니버스가 생성되었습니다.")
                        )
                ));

        List<FileJpaEntity> fileInDB = fileJpaRepository.findAll();
        assertThat(fileInDB).hasSize(2);
        assertThat(fileInDB)
                .anySatisfy(fileJpaEntity -> assertThat(fileJpaEntity.getRealFileName()).isEqualTo("new_universe_thumb.png"))
                .anySatisfy(fileJpaEntity -> assertThat(fileJpaEntity.getRealFileName()).isEqualTo("new_universe_music.mp3"));
    }
}