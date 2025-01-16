package com.hoo.aoo.aar.application.port.out.database;

import com.hoo.aoo.aar.domain.account.SnsAccount;
import com.hoo.aoo.aar.domain.exception.InvalidPhoneNumberException;
import com.hoo.aoo.aar.domain.account.SnsDomain;

import java.util.Optional;

public interface FindSnsAccountPort {
    Optional<SnsAccount> load(SnsDomain domain, String snsId) throws InvalidPhoneNumberException;
    Optional<SnsAccount> load(Long id) throws InvalidPhoneNumberException;
}
