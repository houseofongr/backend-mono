package com.aoo.aar.application.port.out.cache;

import java.time.Duration;

public interface SaveEmailAuthnStatePort {
    void saveAuthenticated(String email, Duration ttl);
}
