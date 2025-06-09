package com.aoo.admin.adapter.in.web.universe;

import com.aoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("classpath:/sql/clear.sql")
@Sql("GetUniverseTreeControllerTest.sql")
class GetUniverseTreeControllerTest extends AbstractControllerTest {

    @Test
    @DisplayName("유니버스 내부 트리 조회 API")
    void getUniverseTreeAPI() throws Exception {
        mockMvc.perform(get("/admin/universes/tree/{universeId}", 1)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-universe-get-tree",
                        pathParameters(
                                parameterWithName("universeId").description("조회할 유니버스의 식별자입니다.")
                        ),
                        responseFields(
                                fieldWithPath("universeId").description("유니버스의 식별자입니다."),
                                fieldWithPath("innerImageId").description("유니버스의 내부 이미지파일 식별자입니다."),
                                fieldWithPath("spaces").description("유니버스 내부의 스페이스입니다."),
                                fieldWithPath("elements").description("유니버스 내부의 엘리먼트입니다."),

                                fieldWithPath("spaces[].spaceId").description("스페이스의 식별자입니다."),
                                fieldWithPath("spaces[].parentSpaceId").description("스페이스의 부모 스페이스 식별자입니다. +" + "\n" + "* 부모가 유니버스일 경우, -1"),
                                fieldWithPath("spaces[].innerImageId").description("스페이스의 내부 이미지파일 식별자입니다."),
                                fieldWithPath("spaces[].depth").description("스페이스의 깊이입니다."),
                                fieldWithPath("spaces[].title").description("스페이스의 제목입니다."),
                                fieldWithPath("spaces[].description").description("스페이스의 설명입니다."),
                                fieldWithPath("spaces[].startX").description("스페이스의 시작좌표(X)입니다."),
                                fieldWithPath("spaces[].startY").description("스페이스의 시작좌표(Y)입니다."),
                                fieldWithPath("spaces[].endX").description("스페이스의 종료좌표(X)입니다."),
                                fieldWithPath("spaces[].endY").description("스페이스의 종료좌표(Y)입니다."),
                                subsectionWithPath("spaces[].elements").description("스페이스 내부의 엘리먼트입니다."),
                                subsectionWithPath("spaces[].spaces").description("스페이스 내부의 스페이스입니다.(무한 depth)"),

                                fieldWithPath("elements[].elementId").description("엘리먼트의 식별자입니다."),
                                fieldWithPath("elements[].parentSpaceId").description("엘리먼트의 부모 스페이스 식별자입니다. +" + "\n" + "* 부모가 유니버스일 경우, -1"),
                                fieldWithPath("elements[].innerImageId").description("엘리먼트의 내부 이미지파일 식별자입니다."),
                                fieldWithPath("elements[].depth").description("엘리먼트의 깊이입니다."),
                                fieldWithPath("elements[].title").description("엘리먼트의 제목입니다."),
                                fieldWithPath("elements[].description").description("엘리먼트의 설명입니다."),
                                fieldWithPath("elements[].startX").description("엘리먼트의 시작좌표(X)입니다."),
                                fieldWithPath("elements[].startY").description("엘리먼트의 시작좌표(Y)입니다."),
                                fieldWithPath("elements[].endX").description("엘리먼트의 종료좌표(X)입니다."),
                                fieldWithPath("elements[].endY").description("엘리먼트의 종료좌표(Y)입니다.")
                        )
                ));
    }
}