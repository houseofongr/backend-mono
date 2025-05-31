package com.aoo.admin.application.port.in.user;

import com.aoo.admin.domain.user.User;

public interface DeleteUserPort {
    void deleteUser(User user);
}
