package com.hoo.aoo.aar.adapter.in.web.home;

import com.hoo.aoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetHomeRoomsControllerTest extends AbstractControllerTest {

    @Override
    protected String getBaseUrl() {
        return "api.archiveofongr.site";
    }

    @Test
    @DisplayName("룸 조회 API")
    void testGetHome() throws Exception {
        mockMvc.perform(get("/aar/users/homes/rooms")
                        .param("homeId", "1")
                        .with(jwt().jwt(jwt -> jwt.claim("userId", 10L))
                                .authorities(new SimpleGrantedAuthority("ROLE_USER"))
                        )
                )
                .andExpect(status().is(200))
                .andDo(document("aar-home-get-rooms",
                        queryParameters(
                                parameterWithName("homeId").description("조회할 홈의 식별자입니다.")
                        ),
                        responseFields(
                                fieldWithPath("homeName").description("홈의 이름입니다."),

                                fieldWithPath("house.width").description("하우스의 가로 길이입니다."),
                                fieldWithPath("house.height").description("하우스의 높이입니다."),
                                fieldWithPath("house.borderImageId").description("하우스 테두리 이미지의 식별자입니다."),

                                fieldWithPath("rooms[].roomId").description("룸의 식별자입니다."),
                                fieldWithPath("rooms[].name").description("룸의 이름입니다."),
                                fieldWithPath("rooms[].x").description("룸의 시작점(X좌표)입니다."),
                                fieldWithPath("rooms[].y").description("룸의 시작점(Y좌표)입니다."),
                                fieldWithPath("rooms[].z").description("룸의 시작점(Z좌표)입니다."),
                                fieldWithPath("rooms[].width").description("룸의 가로 길이입니다."),
                                fieldWithPath("rooms[].height").description("룸의 높이입니다."),
                                fieldWithPath("rooms[].imageId").description("룸의 이미지 식별자입니다.")
                        )
                ));
    }
}