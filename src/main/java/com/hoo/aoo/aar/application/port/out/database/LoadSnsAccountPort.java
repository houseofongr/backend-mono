package com.hoo.aoo.aar.application.port.out.database;

import com.hoo.aoo.aar.domain.SnsAccount;

import java.util.Optional;

public interface LoadSnsAccountPort {
    Optional<SnsAccount> loadWithUser(String snsId);
    Optional<SnsAccount> load(Long id);
}
