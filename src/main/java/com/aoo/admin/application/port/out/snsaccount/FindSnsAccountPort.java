package com.aoo.admin.application.port.out.snsaccount;

import com.aoo.admin.domain.user.snsaccount.SnsAccount;
import com.aoo.admin.domain.user.snsaccount.SnsDomain;

import java.util.Optional;

public interface FindSnsAccountPort {
    Optional<SnsAccount> loadSnsAccount(SnsDomain domain, String snsId);
    Optional<SnsAccount> loadSnsAccount(Long id);
}
