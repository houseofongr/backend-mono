package com.aoo.admin.adapter.in.web.category;

import com.aoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetCategoryControllerTest extends AbstractControllerTest {

    @Test
    @DisplayName("카테고리 조회 API")
    void getCategories() throws Exception {

//        mockMvc.perform(get("/admin/categories")
//                        .with(user("admin").roles("ADMIN")))
//                .andExpect(status().is(200))
//                .andDo(document("admin-category-get",
//                        responseFields(
//                                fieldWithPath("categories[].id").description("카테고리의 아이디입니다."),
//                                fieldWithPath("categories[].name").description("카테고리의 이름입니다.")
//                        )
//                ));
    }

}