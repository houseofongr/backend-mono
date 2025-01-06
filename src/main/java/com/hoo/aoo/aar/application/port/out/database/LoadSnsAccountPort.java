package com.hoo.aoo.aar.application.port.out.database;

import com.hoo.aoo.aar.domain.account.SnsAccount;
import com.hoo.aoo.aar.domain.exception.InvalidPhoneNumberException;
import com.hoo.aoo.common.enums.SnsDomain;

import java.util.Optional;

public interface LoadSnsAccountPort {
    Optional<SnsAccount> load(SnsDomain domain, String snsId) throws InvalidPhoneNumberException;
    Optional<SnsAccount> load(Long id) throws InvalidPhoneNumberException;
}
