package com.aoo.aar.adapter.out.cache;

import com.aoo.aar.application.port.out.cache.SaveEmailAuthnCodePort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedisAdapter implements SaveEmailAuthnCodePort {

    private final ValueOperations<String, String> valueOperations;

    public RedisAdapter(RedisTemplate<String, String> redisTemplate) {
        this.valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public void saveEmailAuthnCode(String email, String code, Duration ttl) {
        valueOperations.set(email, code, ttl);
    }
}
