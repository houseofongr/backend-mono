package com.hoo.aoo.aar.application.port.out.snsaccount;

import com.hoo.aoo.aar.domain.user.snsaccount.SnsAccount;
import com.hoo.aoo.aar.domain.user.snsaccount.SnsDomain;

import java.util.Optional;

public interface FindSnsAccountPort {
    Optional<SnsAccount> load(SnsDomain domain, String snsId);
    Optional<SnsAccount> load(Long id);
}
