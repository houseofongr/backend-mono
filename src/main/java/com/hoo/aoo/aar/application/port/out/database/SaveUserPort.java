package com.hoo.aoo.aar.application.port.out.database;

import com.hoo.aoo.aar.domain.User;

public interface SaveUserPort {
    User save(User user);
}
