package com.hoo.aar.application.port.out.database;

import com.hoo.aar.domain.User;

public interface SaveUserPort {
    User save(User user);
}
