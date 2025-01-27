package com.hoo.aoo.admin.adapter.in.web.soundsource;

import com.hoo.aoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeleteSoundSourceControllerTest extends AbstractControllerTest {

    @Override
    protected String getBaseUrl() {
        return "api.archiveofongr.site";
    }

    @Test
    @DisplayName("음원 삭제 API")
    void testDeleteSoundSourceAPI() throws Exception {
        mockMvc.perform(delete("/admin/sound-sources/{soundSourceId}", 1L))
                .andExpect(status().is(200))
                .andDo(document("admin-soundsource-delete",
                        pathParameters(
                                parameterWithName("soundSourceId").description("삭제할 음원의 식별자입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("삭제 완료 메시지 : 0번 음원이 삭제되었습니다.")
                        )
                ));
    }
}