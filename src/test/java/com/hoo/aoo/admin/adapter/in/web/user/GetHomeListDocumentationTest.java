package com.hoo.aoo.admin.adapter.in.web.user;

import com.hoo.aoo.common.adapter.in.web.config.AbstractDocumentationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetHomeListDocumentationTest extends AbstractDocumentationTest {

    @Override
    protected String getBaseUrl() {
        return "api.archiveofongr.site";
    }

    @Test
    @DisplayName("사용자 홈 조회 테스트")
    void testGetHomeList() throws Exception {
        mockMvc.perform(get("/admin/users/{userId}/homes", 1L))
                .andExpect(status().is(200))
                .andDo(document("admin-user-get-home-list",
                        pathParameters(
                                parameterWithName("userId").description("홈 리스트를 조회할 사용자 식별자입니다.")
                        ),
                        responseFields(
                                fieldWithPath("homes.content[].id").description("홈의 아이디입니다."),
                                fieldWithPath("homes.content[].title").description("하우스의 타이틀입니다."),
                                fieldWithPath("homes.content[].author").description("하우스의 작가입니다."),
                                fieldWithPath("homes.content[].description").description("하우스에 대한 설명입니다. +" + "\n" + "* 100자까지만 전송됩니다."),
                                fieldWithPath("homes.content[].createdDate").description("홈이 생성된 날짜입니다."),
                                fieldWithPath("homes.content[].updatedDate").description("홈이 수정된 날짜입니다."),

                                fieldWithPath("homes.pageable").ignored(),
//                                fieldWithPath("homes.pageable.pageNumber").description("현재 페이지 번호입니다."),
//                                fieldWithPath("homes.pageable.pageSize").description("페이지당 항목 수입니다."),
//                                fieldWithPath("homes.pageable.sort.empty").description("정렬이 비어 있는지 여부입니다."),
//                                fieldWithPath("homes.pageable.sort.unsorted").description("정렬되지 않았는지 여부입니다."),
//                                fieldWithPath("homes.pageable.sort.sorted").description("정렬되었는지 여부입니다."),
//                                fieldWithPath("homes.pageable.offset").description("현재 페이지의 시작 항목 인덱스입니다."),
//                                fieldWithPath("homes.pageable.unpaged").description("페이징이 해제되었는지 여부입니다."),
//                                fieldWithPath("homes.pageable.paged").description("페이징이 활성화되었는지 여부입니다."),
//
                                fieldWithPath("homes.size").description("한 페이지의 항목 수입니다."),
                                fieldWithPath("homes.sort.empty").description("정렬이 비어 있는지 여부입니다."),
                                fieldWithPath("homes.sort.unsorted").description("정렬되지 않았는지 여부입니다."),
                                fieldWithPath("homes.sort.sorted").description("정렬되었는지 여부입니다."),
                                fieldWithPath("homes.empty").description("페이지가 비어 있는지 여부입니다."),
                                fieldWithPath("homes.first").description("첫번째 페이지인지 여부입니다."),
                                fieldWithPath("homes.last").description("마지막 페이지인지 여부입니다."),
                                fieldWithPath("homes.number").description("현재 페이지의 번호입니다."),
                                fieldWithPath("homes.numberOfElements").description("현재 페이지에 있는 항목의 수입니다."),
                                fieldWithPath("homes.totalElements").description("조회된 전체 개수입니다."),
                                fieldWithPath("homes.totalPages").description("조회된 전체 페이지 개수입니다.")
                        )
                ));
    }
}