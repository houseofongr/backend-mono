package com.hoo.aoo.aar.adapter.in.web.authn.security.service;

import com.hoo.aoo.aar.adapter.in.web.authn.security.jwt.JwtUtil;
import com.hoo.aoo.aar.adapter.out.persistence.mapper.UserMapper;
import com.hoo.aoo.aar.application.port.out.database.LoadSnsAccountPort;
import com.hoo.aoo.aar.application.port.out.database.SaveSnsAccountPort;
import com.hoo.aoo.aar.domain.DomainFixtureRepository;
import com.hoo.aoo.aar.domain.SnsAccount;
import com.hoo.aoo.aar.domain.SnsAccountF;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.InstanceOfAssertFactories.BOOLEAN;
import static org.mockito.Mockito.*;

class KakaoLoadUserServiceTest {

    KakaoLoadUserService sut;
    LoadSnsAccountPort loadSnsAccountPort;
    SaveSnsAccountPort saveSnsAccountPort;
    UserMapper userMapper;
    JwtUtil jwtUtil;

    @BeforeEach
    void init() {
        loadSnsAccountPort = mock(LoadSnsAccountPort.class);
        saveSnsAccountPort = mock(SaveSnsAccountPort.class);
        userMapper = mock(UserMapper.class);
        jwtUtil = mock(JwtUtil.class);
        sut = new KakaoLoadUserService(loadSnsAccountPort, saveSnsAccountPort, userMapper, jwtUtil);
    }

    @Test
    @DisplayName("DB에 존재하는 계정은 isLogin = false")
    void testExistUser() {
        // given
        OAuth2User user = mock(OAuth2User.class);
        SnsAccount snsAccount = DomainFixtureRepository.getRegisteredSnsAccount();

        // when
        when(loadSnsAccountPort.loadWithUser(any())).thenReturn(Optional.of(snsAccount));
        OAuth2User loadUser = sut.load(user);

        // then
        assertThat(loadUser.getAttributes()).containsKey("nickname");
        assertThat(loadUser.getAttributes()).containsKey("accessToken");
        assertThat(loadUser.getAttributes()).containsKey("provider");
        assertThat(loadUser.getAttributes()).extractingByKey("isFirstLogin", as(BOOLEAN)).isFalse();
    }

    @Test
    @DisplayName("DB에 존재하지 않는 계정은 회원가입, isLogin = ture")
    void testNotExistUser() {
        // given
        OAuth2User user = mock(OAuth2User.class);
        SnsAccount entity = SnsAccountF.KAKAO_NOT_REGISTERED.get();

        // when
        when(loadSnsAccountPort.loadWithUser(any())).thenReturn(Optional.empty());
        when(saveSnsAccountPort.save(any())).thenReturn(entity);
        when(userMapper.kakaoUserToSnsAccount(any())).thenReturn(entity);
        OAuth2User loadUser = sut.load(user);

        // then
        verify(saveSnsAccountPort, times(1)).save(any());
        assertThat(loadUser.getAttributes()).extractingByKey("isFirstLogin", as(BOOLEAN)).isTrue();
    }

    @Test
    @DisplayName("DB에 존재하지만 사용자와 연동되지 않은 계정은 isLogin = true")
    void testNotRegisteredSnsAccount() {
        // given
        OAuth2User user = mock(OAuth2User.class);
        SnsAccount entity = SnsAccountF.KAKAO_NOT_REGISTERED.get();

        // when
        when(loadSnsAccountPort.loadWithUser(any())).thenReturn(Optional.of(entity));
        OAuth2User loadUser = sut.load(user);

        // then
        verify(saveSnsAccountPort, times(0)).save(any());
        assertThat(loadUser.getAttributes()).extractingByKey("isFirstLogin", as(BOOLEAN)).isTrue();
    }
}