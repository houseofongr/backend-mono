package com.hoo.aoo.admin.adapter.in.web.home;

import com.hoo.aoo.common.adapter.out.persistence.repository.HomeJpaRepository;
import com.hoo.aoo.common.adapter.out.persistence.repository.ItemJpaRepository;
import com.hoo.aoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeleteHomeControllerTest extends AbstractControllerTest {

    @Autowired
    HomeJpaRepository homeJpaRepository;

    @Autowired
    ItemJpaRepository itemJpaRepository;

    @Override
    protected String getBaseUrl() {
        return "api.archiveofongr.site";
    }



    @Test
    @Sql("DeleteHomeControllerTest.sql")
    @DisplayName("홈 삭제 API")
    void testDeleteHomeAPI() throws Exception {
        mockMvc.perform(delete("/admin/homes/{homeId}", 1L))
                .andExpect(status().is(200))
                .andDo(document("admin-home-delete",
                        pathParameters(
                                parameterWithName("homeId").description("삭제할 홈 식별자입니다.")
                        ),
                        responseFields(fieldWithPath("message").description("삭제 완료 메시지 : 0번 홈이 삭제되었습니다."))
                ));

        assertThat(homeJpaRepository.findById(1L)).isEmpty();
        assertThat(itemJpaRepository.findAll()).isEmpty();
    }
}