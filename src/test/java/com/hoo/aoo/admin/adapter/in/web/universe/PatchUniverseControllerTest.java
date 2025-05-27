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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("PatchUniverseControllerTest.sql")
class PatchUniverseControllerTest extends AbstractControllerTest {

    @Override
    public boolean useSpringSecurity() {
        return false;
    }

    @Test
    @DisplayName("유니버스 수정 API")
    void testUniverseUpdateAPI() throws Exception {
        //language=JSON
        String command = """
            {
              "title": "오르트구름",
              "description": "오르트구름은 태양계 최외곽에 위치하고 있습니다.",
              "category": "LIFE",
              "publicStatus": "PRIVATE",
              "tags": [
                "오르트구름", "태양계", "윤하", "별"
              ]
            }
            """;

        mockMvc.perform(patch("/admin/universes/update/1")
                        .content(command)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-universe-post-update",
                        requestFields(
                                fieldWithPath("title").description("수정할 제목입니다."),
                                fieldWithPath("description").description("수정할 상세정보입니다."),
                                fieldWithPath("category").description("수정할 카테고리입니다."),
                                fieldWithPath("publicStatus").description("공개 여부입니다."),
                                fieldWithPath("tags").description("수정할 태그 정보입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("수정 완료 메시지 : 0번 유니버스가 수정되었습니다.")
                        )
                ));
    }
}