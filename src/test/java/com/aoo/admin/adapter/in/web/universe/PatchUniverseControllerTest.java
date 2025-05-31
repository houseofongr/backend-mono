package com.aoo.admin.adapter.in.web.universe;

import com.aoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("PatchUniverseControllerTest.sql")
class PatchUniverseControllerTest extends AbstractControllerTest {

    @Override
    public boolean useSpringSecurity() {
        return false;
    }

    @Test
    @DisplayName("특정 유니버스 정보 수정 API")
    void testUniverseUpdateAPI() throws Exception {
        //language=JSON
        String request = """
                {
                  "title": "오르트구름",
                  "description": "오르트구름은 태양계 최외곽에 위치하고 있습니다.",
                  "category": "LIFE",
                  "publicStatus": "PRIVATE",
                  "hashtags": [
                    "오르트구름", "태양계", "윤하", "별"
                  ]
                }
                """;

        mockMvc.perform(patch("/admin/universes/{universeId}", 1)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-universe-patch",
                        pathParameters(
                                parameterWithName("universeId").description("수정할 유니버스의 식별자입니다.")
                        ),
                        requestFields(
                                fieldWithPath("title").description("수정할 제목입니다."),
                                fieldWithPath("description").description("수정할 상세정보입니다."),
                                fieldWithPath("category").description("수정할 카테고리입니다."),
                                fieldWithPath("publicStatus").description("공개 여부입니다."),
                                fieldWithPath("hashtags").description("수정할 태그 정보입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("수정 완료 메시지 : 0번 유니버스가 수정되었습니다.")
                        )
                ));
    }
}