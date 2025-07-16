package com.hoo.main.application.port.out.cache;

public interface LoadEmailAuthnStatePort {
    boolean loadAuthenticated(String email);
}
