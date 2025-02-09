package com.hoo.aoo.aar.application.service;

import com.hoo.aoo.aar.application.port.in.authn.OAuth2Dto;
import com.hoo.aoo.aar.adapter.out.jwt.JwtUtil;
import com.hoo.aoo.aar.application.port.out.persistence.snsaccount.CreateSnsAccountPort;
import com.hoo.aoo.aar.application.port.out.persistence.snsaccount.FindSnsAccountPort;
import com.hoo.aoo.aar.application.port.out.persistence.snsaccount.SaveSnsAccountPort;
import com.hoo.aoo.aar.application.port.out.persistence.user.FindUserPort;
import com.hoo.aoo.aar.application.service.authn.LoadKakaoSnsAccountService;
import com.hoo.aoo.aar.domain.exception.InvalidPhoneNumberException;
import com.hoo.aoo.aar.domain.user.User;
import com.hoo.aoo.aar.domain.user.UserId;
import com.hoo.aoo.aar.domain.user.UserInfo;
import com.hoo.aoo.aar.domain.user.snsaccount.SnsAccount;
import com.hoo.aoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;
import java.util.Optional;

import static com.hoo.aoo.common.util.GsonUtil.gson;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.InstanceOfAssertFactories.BOOLEAN;
import static org.mockito.Mockito.*;

class LoadKakaoSnsAccountServiceTest {

    LoadKakaoSnsAccountService sut;

    FindSnsAccountPort findSnsAccountPort;
    FindUserPort findUserPort;
    CreateSnsAccountPort createSnsAccountPort;
    SaveSnsAccountPort saveSnsAccountPort;

    JwtUtil jwtUtil;

    @BeforeEach
    void init() {
        findSnsAccountPort = mock();
        findUserPort = mock();
        createSnsAccountPort = mock();
        saveSnsAccountPort = mock();
        jwtUtil = mock(JwtUtil.class);
        sut = new LoadKakaoSnsAccountService(findSnsAccountPort, findUserPort, createSnsAccountPort, saveSnsAccountPort, jwtUtil);
    }

    @Test
    @DisplayName("DB에 존재하는 계정은 isFirstLogin = false")
    void testExistUser() throws InvalidPhoneNumberException {
        // given
        OAuth2User oAuth2User = mock();
        SnsAccount snsAccount = mock();
        User user = mock();

        // when
        when(findSnsAccountPort.load(any(), any())).thenReturn(Optional.of(snsAccount));
        when(snsAccount.getUserId()).thenReturn(new UserId(1L));
        when(findUserPort.load(1L)).thenReturn(Optional.of(user));
        when(user.getUserInfo()).thenReturn(new UserInfo("남상엽","leaf", "test@example.com"));
        OAuth2User loadUser = sut.load(oAuth2User);

        // then
        assertThat(loadUser.getAttributes()).containsKey("nickname");
        assertThat(loadUser.getAttributes()).containsKey("accessToken");
        assertThat(loadUser.getAttributes()).containsKey("provider");
        assertThat(loadUser.getAttributes()).extractingByKey("isFirstLogin", as(BOOLEAN)).isFalse();
    }

    @Test
    @DisplayName("DB에 존재하지 않는 계정은 회원가입, isFirstLogin = true")
    void testNotExistUser() {
        // given
        OAuth2User user = mock(OAuth2User.class);
        SnsAccount entity = MockEntityFactoryService.getSnsAccount();
        OAuth2Dto.KakaoUserInfo userInfo = new OAuth2Dto.KakaoUserInfo(entity.getSnsAccountId().getSnsId(),
                new OAuth2Dto.KakaoUserInfo.KakaoAccount(entity.getSnsAccountInfo().getEmail(), true, true, true,
                        new OAuth2Dto.KakaoUserInfo.KakaoAccount.Profile(entity.getSnsAccountInfo().getNickname(), true)));
        Map<String, Object> attributes = gson.fromJson(gson.toJsonTree(userInfo), Map.class);

        // when
        when(user.getAttributes()).thenReturn(attributes);
        when(findSnsAccountPort.load(any(), any())).thenReturn(Optional.empty());
        when(createSnsAccountPort.createSnsAccount(any(),any(),any(),any(),any())).thenReturn(MockEntityFactoryService.getSnsAccount());
        OAuth2User loadUser = sut.load(user);

        // then
        verify(saveSnsAccountPort, times(1)).save(any());
        assertThat(loadUser.getAttributes()).extractingByKey("nickname").isEqualTo("leaf");
        assertThat(loadUser.getAttributes()).extractingByKey("isFirstLogin", as(BOOLEAN)).isTrue();
    }

    @Test
    @DisplayName("DB에 존재하지만 사용자와 연동되지 않은 계정은 isFirstLogin = true")
    void testNotRegisteredSnsAccount() {
        // given
        OAuth2User user = mock(OAuth2User.class);
        SnsAccount snsAccount = MockEntityFactoryService.getSnsAccount();

        // when
        when(findSnsAccountPort.load(any(), any())).thenReturn(Optional.of(snsAccount));
        OAuth2User loadUser = sut.load(user);

        // then
        verify(saveSnsAccountPort, times(0)).save(any());
        assertThat(loadUser.getAttributes()).extractingByKey("isFirstLogin", as(BOOLEAN)).isTrue();
    }
}