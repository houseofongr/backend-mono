package com.hoo.aar.application.port.out.database;

import com.hoo.aar.domain.SnsAccount;

import java.util.Optional;

public interface LoadSnsAccountPort {
    Optional<SnsAccount> loadNullableWithUser(String snsId);
    SnsAccount load(Long id);
}
