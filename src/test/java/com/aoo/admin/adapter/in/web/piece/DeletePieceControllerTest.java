package com.aoo.admin.adapter.in.web.piece;

import com.aoo.common.adapter.in.web.config.AbstractControllerTest;
import com.aoo.common.adapter.out.persistence.repository.PieceJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("DeletePieceControllerTest.sql")
class DeletePieceControllerTest extends AbstractControllerTest {

    @Autowired
    PieceJpaRepository pieceJpaRepository;

    @Test
    @DisplayName("피스 삭제 API")
    void testDeletePiece() throws Exception {

        mockMvc.perform(delete("/admin/pieces/{pieceId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-piece-delete",
                        pathParameters(
                                parameterWithName("pieceId").description("삭제할 피스 식별자입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("삭제 완료 메시지 : '[#id]번 피스가 삭제되었습니다.'")
                        )
                ));

        assertThat(pieceJpaRepository.findAll()).noneMatch(pieceJpaEntity -> pieceJpaEntity.getId().equals(1L));
    }
}