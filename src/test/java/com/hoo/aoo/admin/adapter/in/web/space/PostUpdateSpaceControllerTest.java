package com.hoo.aoo.admin.adapter.in.web.space;

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

@Sql("PostUpdateSpaceControllerTest.sql")
class PostUpdateSpaceControllerTest extends AbstractControllerTest {

    @Autowired
    FileJpaRepository fileJpaRepository;
    //language=JSON
    String metadata = """
            {
              "targetId": 1,
              "title": "블랙홀",
              "description": "블랙홀은 빛도 빨아들입니다.",
              "dx": 0.1,
              "dy": 0.2,
              "scaleX": 0.3,
              "scaleY": 0.4
            }
            """;

    @Override
    public boolean useSpringSecurity() {
        return false;
    }

    @Test
    @DisplayName("이미지 수정하지 않음")
    void testNotModifyImage() throws Exception {
        MockPart metadataPart = new MockPart("metadata", metadata.getBytes());
        metadataPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(multipart("/admin/spaces/update")
                        .part(metadataPart)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200));
    }

    @Test
    @DisplayName("스페이스 수정 API")
    void testSpaceUpdateAPI() throws Exception {
        saveFile(FileF.IMAGE_FILE_1.get(tempDir.toString()));
        saveFile(FileF.IMAGE_FILE_2.get(tempDir.toString()));
        saveFile(FileF.AUDIO_FILE_1.get(tempDir.toString()));

        MockPart metadataPart = new MockPart("metadata", metadata.getBytes());
        metadataPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        MockMultipartFile image = new MockMultipartFile("image", "new_image.png", "image/png", "space image file".getBytes());

        mockMvc.perform(multipart("/admin/spaces/update")
                        .file(image)
                        .part(metadataPart)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-space-post-update",
                        requestParts(
                                partWithName("metadata").description("수정할 스페이스의 정보를 포함하는 Json 형태의 문자열입니다."),
                                partWithName("image").description("수정할 스페이스의 내부 이미지입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("수정 완료 메시지 : 0번 스페이스가 수정되었습니다.")
                        )
                ));

        List<FileJpaEntity> fileInDB = fileJpaRepository.findAll();
        assertThat(fileInDB)
                .anySatisfy(fileJpaEntity -> assertThat(fileJpaEntity.getRealFileName()).isEqualTo("new_image.png"))
                .noneSatisfy(fileJpaEntity -> assertThat(fileJpaEntity.getId()).isEqualTo(3));
    }
}