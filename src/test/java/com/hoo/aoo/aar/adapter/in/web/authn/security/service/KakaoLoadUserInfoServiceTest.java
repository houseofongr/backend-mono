package com.hoo.aoo.aar.adapter.in.web.authn.security.service;

import com.hoo.aoo.aar.adapter.in.web.authn.security.dto.OAuth2Dto;
import com.hoo.aoo.aar.adapter.in.web.authn.security.jwt.JwtUtil;
import com.hoo.aoo.common.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import com.hoo.aoo.aar.adapter.out.persistence.mapper.UserMapper;
import com.hoo.aoo.aar.adapter.out.persistence.repository.SnsAccountJpaRepository;
import com.hoo.aoo.aar.domain.DomainFixtureRepository;
import com.hoo.aoo.aar.domain.account.SnsAccount;
import com.hoo.aoo.aar.domain.SnsAccountF;
import com.hoo.aoo.aar.domain.exception.InvalidPhoneNumberException;
import com.nimbusds.jose.shaded.gson.Gson;
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

class KakaoLoadUserInfoServiceTest {

    KakaoLoadUserService sut;
    SnsAccountJpaRepository repository;
    JwtUtil jwtUtil;
    UserMapper userMapper;

    @BeforeEach
    void init() {
        repository = mock(SnsAccountJpaRepository.class);
        jwtUtil = mock(JwtUtil.class);
        sut = new KakaoLoadUserService(repository, jwtUtil);
        userMapper = new UserMapper();
    }

    @Test
    @DisplayName("DB에 존재하는 계정은 isFirstLogin = false")
    void testExistUser() throws InvalidPhoneNumberException {
        // given
        OAuth2User user = mock(OAuth2User.class);
        SnsAccount registeredSnsAccount = DomainFixtureRepository.getRegisteredSnsAccount();
        SnsAccountJpaEntity snsAccount = userMapper.mapToNewJpaEntity(registeredSnsAccount);
        UserJpaEntity jpaEntity = mock(UserJpaEntity.class);
        snsAccount.setUserEntity(jpaEntity);

        // when
        when(repository.findWithUserEntity(any(), any())).thenReturn(Optional.of(snsAccount));
        when(jpaEntity.getNickname()).thenReturn("leaf");
        OAuth2User loadUser = sut.load(user);

        // then
        assertThat(loadUser.getAttributes()).containsKey("nickname");
        assertThat(loadUser.getAttributes()).containsKey("accessToken");
        assertThat(loadUser.getAttributes()).containsKey("provider");
        assertThat(loadUser.getAttributes()).extractingByKey("isFirstLogin", as(BOOLEAN)).isFalse();
    }

    @Test
    @DisplayName("DB에 존재하지 않는 계정은 회원가입, isFirstLogin = true")
    void testNotExistUser() throws InvalidPhoneNumberException {
        // given
        OAuth2User user = mock(OAuth2User.class);
        SnsAccountJpaEntity entity = userMapper.mapToNewJpaEntity(SnsAccountF.REGISTERED_KAKAO_2.get());
        OAuth2Dto.KakaoUserInfo userInfo = new OAuth2Dto.KakaoUserInfo(entity.getSnsId(),
                new OAuth2Dto.KakaoUserInfo.KakaoAccount(entity.getEmail(), true, true, true,
                        new OAuth2Dto.KakaoUserInfo.KakaoAccount.Profile(entity.getNickname(),true)));
        Map<String, Object> attributes = gson.fromJson(gson.toJsonTree(userInfo), Map.class);

        // when
        when(user.getAttributes()).thenReturn(attributes);
        when(repository.findWithUserEntity(any(), any())).thenReturn(Optional.empty());
        when(repository.save(any())).thenReturn(entity);
        OAuth2User loadUser = sut.load(user);

        // then
        verify(repository, times(1)).save(any());
        assertThat(loadUser.getAttributes()).extractingByKey("nickname").isEqualTo("spearoad");
        assertThat(loadUser.getAttributes()).extractingByKey("isFirstLogin", as(BOOLEAN)).isTrue();
    }

    @Test
    @DisplayName("DB에 존재하지만 사용자와 연동되지 않은 계정은 isFirstLogin = true")
    void testNotRegisteredSnsAccount() throws InvalidPhoneNumberException {
        // given
        OAuth2User user = mock(OAuth2User.class);
        SnsAccountJpaEntity snsAccount = userMapper.mapToNewJpaEntity(DomainFixtureRepository.getRegisteredSnsAccount());

        // when
        when(repository.findWithUserEntity(any(), any())).thenReturn(Optional.of(snsAccount));
        OAuth2User loadUser = sut.load(user);

        // then
        verify(repository, times(0)).save(any());
        assertThat(loadUser.getAttributes()).extractingByKey("isFirstLogin", as(BOOLEAN)).isTrue();
    }
}