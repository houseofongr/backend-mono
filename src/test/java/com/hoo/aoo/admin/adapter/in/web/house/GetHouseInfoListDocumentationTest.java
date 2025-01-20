package com.hoo.aoo.admin.adapter.in.web.house;

import com.hoo.aoo.common.adapter.in.web.config.AbstractDocumentationTest;
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

public class GetHouseInfoListDocumentationTest extends AbstractDocumentationTest {
    @Override
    protected String getBaseUrl() {
        return "api.archiveofongr.site";
    }

    @Test
    @Sql("GetHouseListDocumentationTest.sql")
    @DisplayName("하우스 리스트 조회 API")
    void testGetList() throws Exception {
        mockMvc.perform(get("/admin/houses?page=1&size=9")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-house-get-list",
                        pathParameters(
                                parameterWithName("page").description("보여줄 페이지 번호입니다. +" + "\n" + "* 기본값 : 1").optional(),
                                parameterWithName("size").description("한 페이지에 보여줄 데이터 개수입니다. +" + "\n" + "* 기본값 : 10").optional(),
                                parameterWithName("searchType").description("검색 방법입니다.").optional(),
                                parameterWithName("keyword").description("검색 키워드입니다.").optional()
                        ),
                        responseFields(
                                fieldWithPath("houses.content[].id").description("하우스의 아이디입니다."),
                                fieldWithPath("houses.content[].title").description("하우스의 제목입니다."),
                                fieldWithPath("houses.content[].author").description("하우스의 작가입니다."),
                                fieldWithPath("houses.content[].description").description("하우스에 대한 설명입니다. +" + "\n" + "* 100자까지만 전송됩니다."),
                                fieldWithPath("houses.content[].createdDate").description("하우스가 생성된 날짜입니다."),
                                fieldWithPath("houses.content[].updatedDate").description("하우스가 최종 수정된 날짜입니다."),
                                fieldWithPath("houses.content[].imageId").description("하우스 기본 이미지의 ID입니다."),

                                fieldWithPath("houses.pageable.pageNumber").description("현재 페이지 번호입니다."),
                                fieldWithPath("houses.pageable.pageSize").description("페이지당 항목 수입니다."),
                                fieldWithPath("houses.pageable.sort.empty").description("정렬이 비어 있는지 여부입니다."),
                                fieldWithPath("houses.pageable.sort.unsorted").description("정렬되지 않았는지 여부입니다."),
                                fieldWithPath("houses.pageable.sort.sorted").description("정렬되었는지 여부입니다."),
                                fieldWithPath("houses.pageable.offset").description("현재 페이지의 시작 항목 인덱스입니다."),
                                fieldWithPath("houses.pageable.unpaged").description("페이징이 해제되었는지 여부입니다."),
                                fieldWithPath("houses.pageable.paged").description("페이징이 활성화되었는지 여부입니다."),

                                fieldWithPath("houses.size").description("한 페이지의 항목 수입니다."),
                                fieldWithPath("houses.sort.empty").description("정렬이 비어 있는지 여부입니다."),
                                fieldWithPath("houses.sort.unsorted").description("정렬되지 않았는지 여부입니다."),
                                fieldWithPath("houses.sort.sorted").description("정렬되었는지 여부입니다."),
                                fieldWithPath("houses.empty").description("페이지가 비어 있는지 여부입니다."),
                                fieldWithPath("houses.first").description("첫번째 페이지인지 여부입니다."),
                                fieldWithPath("houses.last").description("마지막 페이지인지 여부입니다."),
                                fieldWithPath("houses.number").description("현재 페이지의 번호입니다."),
                                fieldWithPath("houses.numberOfElements").description("현재 페이지에 있는 항목의 수입니다."),
                                fieldWithPath("houses.totalElements").description("조회된 전체 개수입니다."),
                                fieldWithPath("houses.totalPages").description("조회된 전체 페이지 개수입니다.")
                        )
                ));
    }

}
