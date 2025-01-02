package com.hoo.aar.adapter.in.web.authn.security.service;

import com.hoo.aar.adapter.in.web.authn.security.jwt.JwtUtil;
import com.hoo.aar.adapter.out.persistence.mapper.UserMapper;
import com.hoo.aar.application.port.out.database.LoadSnsAccountPort;
import com.hoo.aar.application.port.out.database.SaveSnsAccountPort;
import com.hoo.aar.domain.DomainFixtureRepository;
import com.hoo.aar.domain.SnsAccount;
import com.hoo.aar.domain.SnsAccountF;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
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
    @DisplayName("DB에 존재하는 계정은 속성 값만 변경 후 반환")
    void testExistUser() {
        // given
        OAuth2User user = mock(OAuth2User.class);
        SnsAccount snsAccount = DomainFixtureRepository.getRegisteredSnsAccount();

        // when
        when(loadSnsAccountPort.loadNullableWithUser((String) any())).thenReturn(Optional.of(snsAccount));
        OAuth2User loadUser = sut.load(user);

        // then
        assertThat(loadUser.getAttributes()).containsKey("nickname");
        assertThat(loadUser.getAttributes()).containsKey("accessToken");
        assertThat(loadUser.getAttributes()).containsKey("provider");
        assertThat(loadUser.getAttributes()).extractingByKey("isFirstLogin", as(InstanceOfAssertFactories.BOOLEAN)).isFalse();
    }

    @Test
    @DisplayName("DB에 존재하지 않는 계정은 회원가입 후 로그인 시도")
    void testNotExistUser() {
        // given
        OAuth2User user = mock(OAuth2User.class);
        SnsAccount entity = SnsAccountF.KAKAO_NOT_REGISTERED.get();

        // when
        when(loadSnsAccountPort.loadNullableWithUser((String) any())).thenReturn(Optional.empty());
        when(saveSnsAccountPort.save(any())).thenReturn(entity);
        when(userMapper.kakaoUserToSnsAccount(any())).thenReturn(entity);
        OAuth2User loadUser = sut.load(user);

        // then
        verify(saveSnsAccountPort, times(1)).save(any());
        assertThat(loadUser.getAttributes()).containsKey("nickname");
        assertThat(loadUser.getAttributes()).containsKey("accessToken");
        assertThat(loadUser.getAttributes()).containsKey("provider");
        assertThat(loadUser.getAttributes()).extractingByKey("isFirstLogin", as(InstanceOfAssertFactories.BOOLEAN)).isTrue();
    }
}