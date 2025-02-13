package com.hoo.aoo.admin.application.port.out.snsaccount;

import com.hoo.aoo.admin.domain.user.snsaccount.SnsAccount;
import com.hoo.aoo.admin.domain.user.snsaccount.SnsDomain;

import java.util.Optional;

public interface FindSnsAccountPort {
    Optional<SnsAccount> load(SnsDomain domain, String snsId);
    Optional<SnsAccount> load(Long id);
}
