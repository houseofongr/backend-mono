package com.hoo.aar.adapter.in.web.authn.springsecurity.service;

import com.hoo.aar.adapter.in.web.authn.springsecurity.util.JwtUtil;
import com.hoo.aar.adapter.out.persistence.entity.SnsAccountJpaEntityF;
import com.hoo.aar.application.port.out.database.LoadSnsAccountPort;
import com.hoo.aar.application.port.out.database.SaveSnsAccountPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class KakaoLoadUserServiceTest {

    KakaoLoadUserService sut;
    LoadSnsAccountPort loadSnsAccountPort;
    SaveSnsAccountPort saveSnsAccountPort;
    JwtUtil jwtUtil;

    @BeforeEach
    void init() {
        loadSnsAccountPort = mock(LoadSnsAccountPort.class);
        saveSnsAccountPort = mock(SaveSnsAccountPort.class);
        jwtUtil = mock(JwtUtil.class);
        sut = new KakaoLoadUserService(loadSnsAccountPort, saveSnsAccountPort, jwtUtil);
    }

    @Test
    @DisplayName("DB에 존재하는 계정은 속성 값만 변경 후 반환")
    void testExistUser() {
        // given
        OAuth2User user = mock(OAuth2User.class);

        // when
        when(loadSnsAccountPort.load(any())).thenReturn(Optional.of(SnsAccountJpaEntityF.KAKAO.get()));
        OAuth2User loadUser = sut.load(user);

        // then
        assertThat(loadUser.getAttributes())
                .anySatisfy((k, v) -> assertThat(k).contains("username"))
                .anySatisfy((k, v) -> assertThat(k).contains("accessToken"))
                .anySatisfy((k, v) -> assertThat(k).contains("provider"))
                .anySatisfy((k, v) -> {
                    assertThat(k).contains("isFirstLogin");
                    assertThat((boolean) v).isFalse();
                });
    }

    @Test
    @DisplayName("DB에 존재하지 않는 계정은 회원가입 후 로그인 시도")
    void testNotExistUser() {
        // given
        OAuth2User user = mock(OAuth2User.class);

        // when
        when(loadSnsAccountPort.load(any())).thenReturn(Optional.empty());
        when(saveSnsAccountPort.save(any())).thenReturn(SnsAccountJpaEntityF.KAKAO.get());
        OAuth2User loadUser = sut.load(user);

        // then
        assertThat(loadUser.getAttributes())
                .anySatisfy((k, v) -> assertThat(k).contains("username"))
                .anySatisfy((k, v) -> assertThat(k).contains("accessToken"))
                .anySatisfy((k, v) -> assertThat(k).contains("provider"))
                .anySatisfy((k, v) -> {
                    assertThat(k).contains("isFirstLogin");
                    assertThat((boolean) v).isTrue();
                });
    }
}