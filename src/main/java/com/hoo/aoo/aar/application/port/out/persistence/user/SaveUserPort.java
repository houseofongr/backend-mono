package com.hoo.aoo.aar.application.port.out.persistence.user;

import com.hoo.aoo.aar.domain.user.User;

public interface SaveUserPort {
    Long save(User user);
}
