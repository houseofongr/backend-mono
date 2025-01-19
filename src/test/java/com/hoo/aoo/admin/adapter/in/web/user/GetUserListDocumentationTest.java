package com.hoo.aoo.admin.adapter.in.web.user;

import com.hoo.aoo.common.adapter.in.web.config.AbstractDocumentationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetUserListDocumentationTest extends AbstractDocumentationTest {

    @Override
    protected String getBaseUrl() {
        return "api.archiveofongr.site";
    }

    @Test
    @DisplayName("사용자 리스트 조회 API")
    void testGetUserList() throws Exception {
        mockMvc.perform(get("/admin/users"))
                .andExpect(status().is(200))
                .andDo(document("admin-user-get-list",
                        responseFields(
                                fieldWithPath("users.content[].id").description("사용자의 식별자입니다."),
                                fieldWithPath("users.content[].name").description("사용자의 이름입니다."),
                                fieldWithPath("users.content[].email").description("사용자의 이메일 주소입니다."),
                                fieldWithPath("users.content[].phoneNumber").description("사용자의 전화번호입니다."),
                                fieldWithPath("users.content[].registeredDate").description("사용자의 등록일입니다."),

                                fieldWithPath("users.pageable.pageNumber").description("현재 페이지 번호입니다."),
                                fieldWithPath("users.pageable.pageSize").description("페이지당 항목 수입니다."),
                                fieldWithPath("users.pageable.sort.empty").description("정렬이 비어 있는지 여부입니다."),
                                fieldWithPath("users.pageable.sort.unsorted").description("정렬되지 않았는지 여부입니다."),
                                fieldWithPath("users.pageable.sort.sorted").description("정렬되었는지 여부입니다."),
                                fieldWithPath("users.pageable.offset").description("현재 페이지의 시작 항목 인덱스입니다."),
                                fieldWithPath("users.pageable.unpaged").description("페이징이 해제되었는지 여부입니다."),
                                fieldWithPath("users.pageable.paged").description("페이징이 활성화되었는지 여부입니다."),

                                fieldWithPath("users.size").description("한 페이지의 항목 수입니다."),
                                fieldWithPath("users.sort.empty").description("정렬이 비어 있는지 여부입니다."),
                                fieldWithPath("users.sort.unsorted").description("정렬되지 않았는지 여부입니다."),
                                fieldWithPath("users.sort.sorted").description("정렬되었는지 여부입니다."),
                                fieldWithPath("users.empty").description("페이지가 비어 있는지 여부입니다."),
                                fieldWithPath("users.first").description("첫번째 페이지인지 여부입니다."),
                                fieldWithPath("users.last").description("마지막 페이지인지 여부입니다."),
                                fieldWithPath("users.number").description("현재 페이지의 번호입니다."),
                                fieldWithPath("users.numberOfElements").description("현재 페이지에 있는 항목의 수입니다."),
                                fieldWithPath("users.totalElements").description("조회된 전체 개수입니다."),
                                fieldWithPath("users.totalPages").description("조회된 전체 페이지 개수입니다.")
                        )
                        ));
    }
}