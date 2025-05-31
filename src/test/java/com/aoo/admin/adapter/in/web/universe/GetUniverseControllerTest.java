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

class GetUniverseControllerTest extends AbstractControllerTest {

    @Test
    @Sql("GetUniverseControllerTest.sql")
    @DisplayName("관리자 유니버스 상세정보 조회")
    void testGetSpecificUniverse() throws Exception {

        mockMvc.perform(get("/admin/universes/{universeId}", 1)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-universe-get",
                        pathParameters(
                                parameterWithName("universeId").description("조회할 유니버스의 식별자입니다.")
                        ),
                        responseFields(
                                fieldWithPath("id").description("유니버스의 아이디입니다."),
                                fieldWithPath("thumbnailId").description("썸네일 파일 식별자입니다."),
                                fieldWithPath("thumbMusicId").description("썸뮤직 파일 식별자입니다."),
                                fieldWithPath("authorId").description("작성자의 식별자입니다."),
                                fieldWithPath("createdTime").description("유닉스 타임스탬프 형식의 생성(등록)일자입니다."),
                                fieldWithPath("updatedTime").description("유닉스 타임스탬프 형식의 생성(등록)일자입니다."),
                                fieldWithPath("view").description("조회수입니다."),
                                fieldWithPath("like").description("좋아요 숫자입니다."),
                                fieldWithPath("title").description("제목입니다."),
                                fieldWithPath("author").description("작성자의 닉네임입니다."),
                                fieldWithPath("description").description("설명입니다."),
                                fieldWithPath("category").description("카테고리입니다."),
                                fieldWithPath("publicStatus").description("공개 여부입니다."),
                                fieldWithPath("hashtags").description("해시태그 리스트입니다.")
                        )
                ));
    }
}