package com.hoo.aoo.aar.application.port.out.database;

import com.hoo.aoo.aar.domain.exception.InvalidPhoneNumberException;
import com.hoo.aoo.aar.domain.user.User;

import java.util.Optional;

public interface FindUserPort {
    Optional<User> load(Long id) throws InvalidPhoneNumberException;
}
