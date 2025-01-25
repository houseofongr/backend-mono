package com.hoo.aoo.aar.application.port.out.database.snsaccount;

import com.hoo.aoo.aar.domain.user.snsaccount.SnsAccount;
import com.hoo.aoo.aar.domain.exception.InvalidPhoneNumberException;
import com.hoo.aoo.aar.domain.user.snsaccount.SnsDomain;

import java.util.Optional;

public interface FindSnsAccountPort {
    Optional<SnsAccount> find(SnsDomain domain, String snsId);
    Optional<SnsAccount> find(Long id);
}
