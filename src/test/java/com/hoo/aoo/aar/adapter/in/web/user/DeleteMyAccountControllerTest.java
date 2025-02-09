package com.hoo.aoo.aar.adapter.in.web.user;

import com.hoo.aoo.aar.adapter.out.persistence.repository.UserJpaRepository;
import com.hoo.aoo.common.adapter.in.web.config.AbstractControllerTest;
import com.hoo.aoo.common.adapter.out.persistence.repository.DeletedUserJpaRepository;
import com.hoo.aoo.common.adapter.out.persistence.repository.HomeJpaRepository;
import com.hoo.aoo.common.adapter.out.persistence.repository.ItemJpaRepository;
import com.hoo.aoo.common.adapter.out.persistence.repository.SoundSourceJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeleteMyAccountControllerTest extends AbstractControllerTest {

    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    HomeJpaRepository homeJpaRepository;

    @Autowired
    ItemJpaRepository itemJpaRepository;

    @Autowired
    SoundSourceJpaRepository soundSourceJpaRepository;

    @Autowired
    DeletedUserJpaRepository deletedUserJpaRepository;

    @Test
    @Sql("DeleteMyAccountControllerTest.sql")
    @DisplayName("회원탈퇴 API")
    void testDeleteMYAccountAPI() throws Exception {
        mockMvc.perform(delete("/aar/users")
                        .with(jwt().jwt(jwt -> jwt.claim("userId", 10L))
                                .authorities(new SimpleGrantedAuthority("ROLE_USER"))
                        ))
                .andExpect(status().is(200))
                .andDo(MockMvcRestDocumentation.document("aar-user-delete",
                        responseFields(
                                fieldWithPath("message").description("탈퇴 완료 메시지 : 회원탈퇴가 완료되었습니다.")
                        )
                ));

        assertThat(userJpaRepository.findById(10L)).isEmpty();
        assertThat(homeJpaRepository.findAllByUserId(10L)).isEmpty();
        assertThat(itemJpaRepository.findAllByUserId(10L)).isEmpty();
        assertThat(soundSourceJpaRepository.findAllByUserId(10L)).isEmpty();
        assertThat(deletedUserJpaRepository.findByMaskedRealName("남*엽")).isNotEmpty();
    }
}