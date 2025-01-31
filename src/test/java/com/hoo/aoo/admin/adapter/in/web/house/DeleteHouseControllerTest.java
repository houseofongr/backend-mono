package com.hoo.aoo.admin.adapter.in.web.house;

import com.hoo.aoo.common.adapter.in.web.config.AbstractControllerTest;
import com.hoo.aoo.file.adapter.out.persistence.entity.FileJpaEntity;
import com.hoo.aoo.file.adapter.out.persistence.repository.FileJpaRepository;
import com.hoo.aoo.file.domain.File;
import com.hoo.aoo.file.domain.FileF;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeleteHouseControllerTest extends AbstractControllerTest {

    @Autowired
    FileJpaRepository fileJpaRepository;

    @Override
    protected String getBaseUrl() {
        return "api.archiveofongr.site";
    }

    @Test
    @Sql("DeleteHouseControllerTest.sql")
    @DisplayName("하우스 삭제 API")
    void testDeleteHouse() throws Exception {

        saveFile(FileF.IMAGE_FILE_1.get(tempDir.toString()));
        saveFile(FileF.IMAGE_FILE_2.get(tempDir.toString()));
        saveFile(FileF.IMAGE_FILE_3.get(tempDir.toString()));
        saveFile(FileF.IMAGE_FILE_4.get(tempDir.toString()));

        mockMvc.perform(delete("/admin/houses/{houseId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-house-delete",
                        pathParameters(
                                parameterWithName("houseId").description("삭제할 하우스의 식별자입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("삭제 완료 메시지 : 0번 하우스가 삭제되었습니다.")
                        )
                ));
    }

}