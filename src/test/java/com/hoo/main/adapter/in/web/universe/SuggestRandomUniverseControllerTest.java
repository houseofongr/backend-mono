package com.hoo.main.adapter.in.web.universe;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("classpath:/sql/clear.sql")
@Sql("classpath:/sql/universe.sql")
class SuggestRandomUniverseControllerTest extends AbstractControllerTest {

    @Test
    @DisplayName("랜덤 유니버스 추천 API")
    void suggestRadomUniverse() throws Exception {

        //language=JSON
        String content = """
                {
                  "exceptIds" : [1, 2, 3, 4]
                }
                """;
        mockMvc.perform(get("/universes/random")
                        .param("size", "4")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andDo(document("suggest-random-universe",
                        pathParameters(
                                parameterWithName("size").description("가져올 데이터 개수입니다.").optional()
                        ),
                        requestFields(
                                fieldWithPath("exceptIds").description("조회 대상에서 제외할 ID입니다.").optional()
                        ),
                        responseFields(
                                fieldWithPath("universes[].id").description("유니버스의 아이디입니다."),
                                fieldWithPath("universes[].thumbnailId").description("썸네일 파일 ID입니다."),
                                fieldWithPath("universes[].title").description("제목입니다."),
                                fieldWithPath("universes[].author").description("작성자의 닉네임입니다.")
                        )
                ));
    }
}