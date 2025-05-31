package com.aoo.admin.adapter.in.web.universe;

import com.aoo.common.adapter.in.web.config.AbstractControllerTest;
import com.aoo.common.adapter.out.persistence.repository.UniverseJpaRepository;
import com.aoo.file.adapter.out.persistence.entity.FileJpaEntity;
import com.aoo.file.domain.FileF;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
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
    UniverseJpaRepository universeJpaRepository;

    @Test
    @DisplayName("유니버스 썸네일 수정 API")
    void testUniverseUpdateThumbnailAPI() throws Exception {
        saveFile(FileF.IMAGE_FILE_1.get(tempDir.toString()));
        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "new_universe_thumb.png", "image/png", "universe file".getBytes());

        mockMvc.perform(multipart("/admin/universes/thumbnail/{universeId}", 1)
                        .file(thumbnail)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-universe-post-update-thumbnail",
                        pathParameters(
                                parameterWithName("universeId").description("수정할 유니버스의 식별자입니다.")
                        ),
                        requestParts(
                                partWithName("thumbnail").description("수정할 유니버스의 썸네일 이미지입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("수정 완료 메시지 : 0번 유니버스의 썸네일이 수정되었습니다.")
                        )
                ));

        List<FileJpaEntity> fileInDB = fileJpaRepository.findAll();
        assertThat(fileInDB).hasSize(1);
        assertThat(fileInDB)
                .anySatisfy(fileJpaEntity -> assertThat(fileJpaEntity.getRealFileName()).isEqualTo("new_universe_thumb.png"));
        assertThat(universeJpaRepository.findById(1L).orElseThrow().getThumbnailFileId()).isEqualTo(fileInDB.getFirst().getId());
    }

    @Test
    @DisplayName("유니버스 썸뮤직 수정 API")
    void testUniverseUpdateThumbMusicAPI() throws Exception {
        saveFile(FileF.IMAGE_FILE_1.get(tempDir.toString()));
        MockMultipartFile thumbMusic = new MockMultipartFile("thumbMusic", "new_universe_thumb.mp3", "audio/mpeg", "universe file".getBytes());

        mockMvc.perform(multipart("/admin/universes/thumb-music/{universeId}", 1)
                        .file(thumbMusic)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-universe-post-update-thumb-music",
                        pathParameters(
                                parameterWithName("universeId").description("수정할 유니버스의 식별자입니다.")
                        ),
                        requestParts(
                                partWithName("thumbMusic").description("수정할 유니버스의 썸뮤직 오디오 파일입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("수정 완료 메시지 : 0번 유니버스의 썸뮤직이 수정되었습니다.")
                        )
                ));

        List<FileJpaEntity> fileInDB = fileJpaRepository.findAll();
        assertThat(fileInDB).hasSize(1);
        assertThat(fileInDB)
                .anySatisfy(fileJpaEntity -> assertThat(fileJpaEntity.getRealFileName()).isEqualTo("new_universe_thumb.mp3"));
        assertThat(universeJpaRepository.findById(1L).orElseThrow().getThumbMusicFileId()).isEqualTo(fileInDB.getFirst().getId());
    }

    @Test
    @DisplayName("유니버스 내부이미지 수정 API")
    void testUniverseUpdateInnerImageAPI() throws Exception {
        saveFile(FileF.IMAGE_FILE_1.get(tempDir.toString()));
        MockMultipartFile innerImage = new MockMultipartFile("innerImage", "new_universe_inner_image.png", "image/png", "universe file".getBytes());

        mockMvc.perform(multipart("/admin/universes/inner-image/{universeId}", 1)
                        .file(innerImage)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-universe-post-update-inner-image",
                        pathParameters(
                                parameterWithName("universeId").description("수정할 유니버스의 식별자입니다.")
                        ),
                        requestParts(
                                partWithName("innerImage").description("수정할 유니버스의 내부 이미지입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("수정 완료 메시지 : 0번 유니버스의 내부 이미지가 수정되었습니다.")
                        )
                ));


        List<FileJpaEntity> fileInDB = fileJpaRepository.findAll();
        assertThat(fileInDB).hasSize(1);
        assertThat(fileInDB)
                .anySatisfy(fileJpaEntity -> assertThat(fileJpaEntity.getRealFileName()).isEqualTo("new_universe_inner_image.png"));
        assertThat(universeJpaRepository.findById(1L).orElseThrow().getInnerImageFileId()).isEqualTo(fileInDB.getFirst().getId());
    }

}