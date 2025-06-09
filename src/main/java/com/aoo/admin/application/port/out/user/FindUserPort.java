package com.aoo.admin.application.port.out.user;

import com.aoo.admin.domain.user.User;

import java.util.Optional;

public interface FindUserPort {
    Optional<User> loadUser(Long id);

    boolean exist(Long id);
}
