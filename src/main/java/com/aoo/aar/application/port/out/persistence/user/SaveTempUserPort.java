package com.aoo.aar.application.port.out.persistence.user;

public interface SaveTempUserPort {
    Long save(String email, String encryptedPassword, String nickname);
}
