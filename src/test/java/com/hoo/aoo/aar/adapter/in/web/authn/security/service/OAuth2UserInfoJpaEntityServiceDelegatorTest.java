package com.hoo.aoo.aar.adapter.in.web.authn.security.service;

import com.hoo.aoo.aar.adapter.in.web.authn.security.service.KakaoLoadUserService;
import com.hoo.aoo.aar.adapter.in.web.authn.security.service.OAuth2UserServiceDelegator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OAuth2UserInfoJpaEntityServiceDelegatorTest {

    OAuth2UserServiceDelegator sut;
    KakaoLoadUserService kakaoLoadUserService;

    @BeforeEach
    void init() {
        kakaoLoadUserService = mock(KakaoLoadUserService.class);
        sut = spy(new OAuth2UserServiceDelegator(kakaoLoadUserService));
    }

    @Test
    @DisplayName("존재하지 않는 로그인 요청 시 오류")
    void testUnsupportedException() {
        // given
        DefaultOAuth2User user = mock(DefaultOAuth2User.class);
        OAuth2UserRequest request = mock(OAuth2UserRequest.class);
        ClientRegistration registration = mock(ClientRegistration.class);

        // when
        doReturn(user).when(sut).loadSuperUser(any());
        when(request.getClientRegistration()).thenReturn(registration);
        when(registration.getRegistrationId()).thenReturn("not exist");

        // then
        assertThatThrownBy(() -> sut.loadUser(request)).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("카카오 로그인 요청 위임")
    void testDelegateKakao() {
        // given
        DefaultOAuth2User user = mock(DefaultOAuth2User.class);
        OAuth2UserRequest request = mock(OAuth2UserRequest.class);
        ClientRegistration registration = mock(ClientRegistration.class);

        // when
        doReturn(user).when(sut).loadSuperUser(any());
        when(request.getClientRegistration()).thenReturn(registration);
        when(registration.getRegistrationId()).thenReturn("kakao");
        sut.loadUser(request);

        // then
        verify(kakaoLoadUserService, times(1)).load(user);
    }
}