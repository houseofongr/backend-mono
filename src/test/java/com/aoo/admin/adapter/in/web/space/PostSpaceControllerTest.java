package com.aoo.admin.adapter.in.web.space;

import com.aoo.common.adapter.in.web.config.AbstractControllerTest;
import com.aoo.file.adapter.out.persistence.entity.FileJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostSpaceControllerTest extends AbstractControllerTest {

    //language=JSON
    String metadata = """
            {
              "universeId": 1,
              "parentSpaceId": 1,
              "title": "공간",
              "description": "스페이스는 공간입니다.",
              "dx": 0.8,
              "dy": 0.7,
              "scaleX": 0.6,
              "scaleY": 0.5
            }
            """;

    @Override
    public boolean useSpringSecurity() {
        return false;
    }

    @Test
    @DisplayName("유니버스 생성 API")
    @Sql("PostSpaceControllerTest.sql")
    void testCreateUniverse() throws Exception {

        MockPart metadataPart = new MockPart("metadata", metadata.getBytes());
        metadataPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        MockMultipartFile image = new MockMultipartFile("image", "inner_image.png", "image/png", "image file".getBytes());

        mockMvc.perform(multipart("/admin/spaces")
                        .file(image)
                        .part(metadataPart)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(201))
                .andDo(document("admin-space-post",
                        requestParts(
                                partWithName("metadata").description("생성할 스페이스의 정보를 포함하는 Json 형태의 문자열입니다."),
                                partWithName("image").description("생성할 스페이스의 내부 이미지입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("생성 완료 메시지 : 0번 스페이스가 생성되었습니다.")
                        )
                ));

        List<FileJpaEntity> fileInDB = fileJpaRepository.findAll();
        assertThat(fileInDB).hasSize(1);
        assertThat(fileInDB)
                .anySatisfy(fileJpaEntity -> assertThat(fileJpaEntity.getRealFileName()).isEqualTo("inner_image.png"));
    }

}