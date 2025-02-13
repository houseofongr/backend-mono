package com.hoo.aoo.admin.application.port.out.user;

import com.hoo.aoo.admin.domain.user.User;

public interface SaveUserPort {
    Long save(User user);
}
