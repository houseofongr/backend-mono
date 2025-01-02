package com.hoo.aar.adapter.in.web.authn.security.jwt;

import com.hoo.aar.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.hoo.aar.adapter.out.persistence.entity.SnsAccountJpaEntityF;
import com.hoo.aar.domain.*;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.*;

class JwtUtilTest {

    JwtUtil sut;
    JwtDecoder jwtDecoder;

    @BeforeEach
    void init() throws KeyLengthException {
        SecureRandom random = new SecureRandom();
        byte[] sharedSecret = new byte[32];
        random.nextBytes(sharedSecret);

        MACSigner macSigner = new MACSigner(sharedSecret);

        sut = new JwtUtil(macSigner, new JwtAttribute(new String(sharedSecret), "aoo", 10000L));
        jwtDecoder = NimbusJwtDecoder.withSecretKey(macSigner.getSecretKey()).build();
    }

    @Test
    @DisplayName("SNS Account 토큰 생성 테스트")
    void testSnsAccountAccessToken() {
        // given
        SnsAccountJpaEntity entity = SnsAccountJpaEntityF.KAKAO.get();

        // when
        Jwt jwt = jwtDecoder.decode(sut.getAccessToken(entity));

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
    @DisplayName("User 토큰 생성 테스트")
    void testUserAccessToken() {
        // given
        User user = DomainFixtureRepository.getRegisteredUser();
        SnsAccount snsAccount = user.getSnsAccounts().getFirst();

        // when
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