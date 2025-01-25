package com.hoo.aoo.admin.application.port.out.user;

import com.hoo.aoo.admin.domain.user.User;

import java.util.Optional;

public interface FindUserPort {
    Optional<User> load(Long id);

    boolean exist(Long id);
}
