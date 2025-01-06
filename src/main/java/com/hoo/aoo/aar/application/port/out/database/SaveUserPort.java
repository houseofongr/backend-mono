package com.hoo.aoo.aar.application.port.out.database;

import com.hoo.aoo.aar.domain.user.User;

public interface SaveUserPort {
    void save(User user);
}
