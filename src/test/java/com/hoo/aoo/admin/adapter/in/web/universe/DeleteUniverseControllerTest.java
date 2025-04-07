package com.hoo.aoo.admin.adapter.in.web.universe;

import com.hoo.aoo.common.adapter.in.web.config.AbstractControllerTest;
import com.hoo.aoo.file.domain.FileF;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeleteUniverseControllerTest extends AbstractControllerTest {

    @Test
    @Sql("DeleteUniverseControllerTest.sql")
    @DisplayName("유니버스 삭제 API")
    void testDeleteUniverse() throws Exception {

        saveFile(FileF.IMAGE_FILE_1.get(tempDir.toString()));
        saveFile(FileF.AUDIO_FILE_1.get(tempDir.toString()));

        mockMvc.perform(delete("/admin/universes/{id}", 12L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-universe-delete",
                        pathParameters(
                                parameterWithName("id").description("삭제할 유니버스의 식별자입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("삭제 완료 메시지 : 0번 유니버스가 삭제되었습니다.")
                        )
                ));
    }
}