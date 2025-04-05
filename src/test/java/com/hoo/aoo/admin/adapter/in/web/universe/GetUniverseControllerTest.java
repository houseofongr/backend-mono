package com.hoo.aoo.admin.adapter.in.web.universe;

import com.hoo.aoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
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
    @DisplayName("유니버스 리스트 조회 API")
    void testGetList() throws Exception {
        mockMvc.perform(get("/admin/universes?page=1&size=10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-universe-get-list",
                        pathParameters(
                                parameterWithName("page").description("보여줄 페이지 번호입니다. +"
                                                                      + "\n" + "* 기본값 : 1").optional(),
                                parameterWithName("size").description("한 페이지에 보여줄 데이터 개수입니다. +"
                                                                      + "\n" + "* 기본값 : 10").optional(),
                                parameterWithName("sortType").description("정렬 방법입니다. +"
                                                                          + "\n" + "[제목, 등록일자, 조회수] +"
                                                                          + "\n" + "* 기본 정렬 : 등록일자 내림차순").optional(),
                                parameterWithName("isAsc").description("정렬 시 오름차순인지 여부입니다.").optional(),
                                parameterWithName("searchType").description("검색 방법입니다. +"
                                                                            + "\n" + "[컨텐츠, 카테고리]").optional(),
                                parameterWithName("keyword").description("검색 키워드입니다. +"
                                                                         + "\n" + "* 카테고리 검색 시 카테고리명과 일치해야 합니다. +"
                                                                         + "\n" + "[GOVERNMENT_AND_PUBLIC_INSTITUTION : 정부/기관] +"
                                                                         + "\n" + "[HEALTH_INSTITUTION : 의료재단] +"
                                                                         + "\n" + "[LIFE : 라이프] +"
                                                                         + "\n" + "[FASHION_AND_BEAUTY : 패션/뷰티]").optional()
                        ),
                        responseFields(
                                fieldWithPath("universes[].id").description("유니버스의 아이디입니다."),
                                fieldWithPath("universes[].thumbnailId").description("썸네일 파일 식별자입니다."),
                                fieldWithPath("universes[].thumbMusicId").description("썸뮤직 파일 식별자입니다."),
                                fieldWithPath("universes[].createdDate").description("유닉스 타임스탬프 형식의 생성(등록)일자입니다."),
                                fieldWithPath("universes[].updatedDate").description("유닉스 타임스탬프 형식의 생성(등록)일자입니다."),
                                fieldWithPath("universes[].viewCount").description("조회수입니다."),
                                fieldWithPath("universes[].likeCount").description("좋아요 숫자입니다."),
                                fieldWithPath("universes[].title").description("제목입니다."),
                                fieldWithPath("universes[].description").description("설명입니다."),
                                fieldWithPath("universes[].category").description("카테고리입니다."),
                                fieldWithPath("universes[].publicStatus").description("공개 여부입니다."),
                                fieldWithPath("universes[].hashtags").description("해시태그 리스트입니다."),

                                fieldWithPath("pagination.pageNumber").description("현재 페이지 번호입니다."),
                                fieldWithPath("pagination.size").description("한 페이지의 항목 수입니다."),
                                fieldWithPath("pagination.totalElements").description("조회된 전체 개수입니다."),
                                fieldWithPath("pagination.totalPages").description("조회된 전체 페이지 개수입니다.")
                        )
                ));
    }


}