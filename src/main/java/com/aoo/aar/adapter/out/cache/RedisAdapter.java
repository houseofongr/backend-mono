package com.aoo.aar.adapter.out.cache;

import com.aoo.aar.application.port.out.cache.LoadEmailAuthnCodePort;
import com.aoo.aar.application.port.out.cache.SaveEmailAuthnCodePort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

import static com.aoo.aar.adapter.out.cache.RedisKeys.*;

@Component
public class RedisAdapter implements SaveEmailAuthnCodePort, LoadEmailAuthnCodePort {

    private final ValueOperations<String, String> valueOperations;

    public RedisAdapter(RedisTemplate<String, String> redisTemplate) {
        this.valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public String loadAuthnCodeByEmail(String email) {
        return valueOperations.get(EMAIL_AUTHN_CODE_PREFIX.getKey() + email);
    }

    @Override
    public void saveEmailAuthnCode(String email, String code, Duration ttl) {
        valueOperations.set(EMAIL_AUTHN_CODE_PREFIX.getKey() + email, code, ttl);
    }
}
