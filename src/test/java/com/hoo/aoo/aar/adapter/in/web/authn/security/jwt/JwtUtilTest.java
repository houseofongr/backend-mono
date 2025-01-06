package com.hoo.aoo.aar.adapter.in.web.authn.security.jwt;

import com.hoo.aoo.aar.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.hoo.aoo.aar.adapter.out.persistence.entity.UserJpaEntity;
import com.hoo.aoo.aar.adapter.out.persistence.mapper.UserMapper;
import com.hoo.aoo.aar.adapter.out.persistence.repository.SnsAccountJpaRepository;
import com.hoo.aoo.aar.domain.DomainFixtureRepository;
import com.hoo.aoo.aar.domain.account.SnsAccount;
import com.hoo.aoo.aar.domain.SnsAccountF;
import com.hoo.aoo.aar.domain.exception.InvalidPhoneNumberException;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtUtilTest {

    JwtUtil sut;
    JwtDecoder jwtDecoder;
    UserMapper userMapper = new UserMapper();
    SnsAccountJpaRepository repository;

    @BeforeEach
    void init() throws KeyLengthException {
        SecureRandom random = new SecureRandom();
        byte[] sharedSecret = new byte[32];
        random.nextBytes(sharedSecret);

        MACSigner macSigner = new MACSigner(sharedSecret);

        repository = mock(SnsAccountJpaRepository.class);

        sut = new JwtUtil(macSigner, new JwtAttribute(new String(sharedSecret), "aoo", 10000L), repository);
        jwtDecoder = NimbusJwtDecoder.withSecretKey(macSigner.getSecretKey()).build();
    }

    @Test
    @DisplayName("SNS Account Jpa Entity 토큰 생성 테스트")
    void testSnsAccountJpaEntityAccessToken() {
        // given
        SnsAccountJpaEntity snsAccount = mock(SnsAccountJpaEntity.class);

        // when
        when(snsAccount.getId()).thenReturn(1L);
        when(snsAccount.getNickname()).thenReturn("leaf");
        Jwt jwt = jwtDecoder.decode(sut.getAccessToken(snsAccount));

        // then
        assertThat(jwt.getClaims()).containsKey("sub");
        assertThat(jwt.getClaims()).containsKey("snsId");
        assertThat(jwt.getClaims()).extractingByKey("userId").isEqualTo(-1L);
        assertThat(jwt.getClaims()).extractingByKey("iss").isEqualTo("aoo");
        assertThat(jwt.getClaims()).extractingByKey("role").isEqualTo("TEMP_USER");
        assertThat(jwt.getClaims()).extractingByKey("exp", as(InstanceOfAssertFactories.INSTANT))
                .isBefore(Instant.now().plus(10000L, ChronoUnit.MILLIS));
    }

    @Test
    @DisplayName("SNS Account 토큰 생성 테스트")
    void testSnsAccountAccessToken() throws InvalidPhoneNumberException {
        // given
        SnsAccount snsAccount = SnsAccountF.NOT_REGISTERED_KAKAO.get();
        SnsAccountJpaEntity snsAccountJpaEntity = mock(SnsAccountJpaEntity.class);
        UserJpaEntity userJpaEntity = mock(UserJpaEntity.class);

        // when
        when(repository.findWithUserEntity(any(),any())).thenReturn(Optional.of(snsAccountJpaEntity));
        when(snsAccountJpaEntity.getId()).thenReturn(1L);
        when(snsAccountJpaEntity.getNickname()).thenReturn("leaf");
        when(snsAccountJpaEntity.getUserEntity()).thenReturn(userJpaEntity);
        when(userJpaEntity.getId()).thenReturn(1L);
        Jwt jwt = jwtDecoder.decode(sut.getAccessToken(snsAccount));

        // then
        assertThat(jwt.getClaims()).containsKey("sub");
        assertThat(jwt.getClaims()).containsKey("snsId");
        assertThat(jwt.getClaims()).extractingByKey("userId").isEqualTo(1L);
        assertThat(jwt.getClaims()).extractingByKey("iss").isEqualTo("aoo");
        assertThat(jwt.getClaims()).extractingByKey("role").isEqualTo("USER");
        assertThat(jwt.getClaims()).extractingByKey("exp", as(InstanceOfAssertFactories.INSTANT))
                .isBefore(Instant.now().plus(10000L, ChronoUnit.MILLIS));
    }
}