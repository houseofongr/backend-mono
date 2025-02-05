package com.hoo.aoo.aar.adapter.in.web.home;

import com.hoo.aoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetSoundSourcesPathControllerTest extends AbstractControllerTest {

    @Test
    @Sql("GetSoundSourcesPathControllerTest.sql")
    @DisplayName("전체 음원 경로조회 API")
    void testGetAllMySoundSourceAPI() throws Exception {
        mockMvc.perform(get("/aar/sound-sources/path")
                        .with(jwt().jwt(jwt -> jwt.claim("userId", 10L))
                                .authorities(new SimpleGrantedAuthority("ROLE_USER"))
                        ))
                .andExpect(status().is(200))
                .andDo(document("aar-home-get-soundsource-path",
                        responseFields(
                                fieldWithPath("soundSources[].name").description("음원의 이름입니다."),
                                fieldWithPath("soundSources[].description").description("음원의 상세설명입니다."),
                                fieldWithPath("soundSources[].createdDate").description("음원의 생성일입니다."),
                                fieldWithPath("soundSources[].updatedDate").description("음원의 수정일입니다."),
                                fieldWithPath("soundSources[].audioFileId").description("음원이 보유한 음악 파일 식별자입니다."),
                                fieldWithPath("soundSources[].homeName").description("음원이 위치한 홈 이름입니다."),
                                fieldWithPath("soundSources[].homeId").description("음원이 위치한 홈 식별자입니다."),
                                fieldWithPath("soundSources[].roomName").description("음원이 위치한 방 이름입니다."),
                                fieldWithPath("soundSources[].roomId").description("음원이 위치한 룸 식별자입니다."),
                                fieldWithPath("soundSources[].itemName").description("음원이 위치한 아이템 이름입니다."),
                                fieldWithPath("soundSources[].itemId").description("음원이 위치한 아이템 식별자입니다.")
                        )
                ));

    }
}