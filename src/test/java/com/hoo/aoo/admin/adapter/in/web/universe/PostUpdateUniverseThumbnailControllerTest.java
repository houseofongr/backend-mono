package com.hoo.aoo.admin.adapter.in.web.universe;

import com.hoo.aoo.common.adapter.in.web.config.AbstractControllerTest;
import com.hoo.aoo.file.adapter.out.persistence.entity.FileJpaEntity;
import com.hoo.aoo.file.domain.FileF;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class PostUpdateUniverseThumbnailControllerTest extends AbstractControllerTest {

    @Test
    @Sql("PostUpdateUniverseThumbnailControllerTest.sql")
    @DisplayName("유니버스 썸네일 수정 API")
    void testUniverseUpdateAPI() throws Exception {
        saveFile(FileF.IMAGE_FILE_1.get(tempDir.toString()));
        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "new_universe_thumb.png", "image/png", "universe file".getBytes());

        mockMvc.perform(multipart("/admin/universes/thumbnail/1")
                        .file(thumbnail)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-universe-post-update-thumbnail",
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
    }

}