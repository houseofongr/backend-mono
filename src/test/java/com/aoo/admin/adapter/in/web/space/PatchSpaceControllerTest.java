package com.aoo.admin.adapter.in.web.space;

import com.aoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("PatchSpaceControllerTest.sql")
class PatchSpaceControllerTest extends AbstractControllerTest {

    @Test
    @DisplayName("스페이스 수정 API")
    void testSpaceUpdateAPI() throws Exception {
        //language=JSON
        String request = """
            {
              "title": "블랙홀",
              "description": "블랙홀은 빛도 빨아들입니다.",
              "dx": 0.1,
              "dy": 0.2,
              "scaleX": 0.3,
              "scaleY": 0.4
            }
            """;

        mockMvc.perform(patch("/admin/spaces/{spaceId}", 1)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-space-patch",
                        pathParameters(
                                parameterWithName("spaceId").description("수정할 스페이스의 식별자입니다.")
                        ),
                        requestFields(
                                fieldWithPath("title").description("수정할 제목입니다."),
                                fieldWithPath("description").description("수정할 상세정보입니다."),
                                fieldWithPath("dx").description("수정할 상대좌표(x)입니다."),
                                fieldWithPath("dy").description("수정할 상대좌표(y)입니다."),
                                fieldWithPath("scaleX").description("수정할 사이즈(x)입니다."),
                                fieldWithPath("scaleY").description("수정할 사이즈(y)입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("수정 완료 메시지 : 0번 스페이스가 수정되었습니다.")
                        )
                ));
    }
}