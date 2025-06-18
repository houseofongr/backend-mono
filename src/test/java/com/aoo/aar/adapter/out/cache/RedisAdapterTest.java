package com.aoo.aar.adapter.out.cache;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

import static com.aoo.aar.adapter.out.cache.RedisKeys.EMAIL_AUTHN_CODE_PREFIX;
import static org.assertj.core.api.Assertions.assertThat;

@RedisTest
class RedisAdapterTest {

    @Autowired
    RedisAdapter sut;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Test
    @DisplayName("이메일 인증코드 저장 테스트")
    void testSaveAuthnEmailCode() {
        // given
        String email = "test@example.com";
        String code = "123456";
        Duration ttl = Duration.ofMinutes(5);

        // when
        sut.saveEmailAuthnCode(email, code, ttl);

        // then
        assertThat(redisTemplate.opsForValue().get(EMAIL_AUTHN_CODE_PREFIX.getKey() + email)).isEqualTo(code);
        assertThat(redisTemplate.getExpire(EMAIL_AUTHN_CODE_PREFIX.getKey() + email)).isEqualTo(300);
    }

    @Test
    @DisplayName("이메일 인증코드 불러오기 테스트")
    void testLoadAuthnEmailCode() {
        // given
        String email = "test@example.com";
        String code = "123456";
        Duration ttl = Duration.ofMinutes(5);
        sut.saveEmailAuthnCode(email, code, ttl);

        // when
        String result = sut.loadAuthnCodeByEmail(email);

        // then
        assertThat(result).isEqualTo(code);
    }
}