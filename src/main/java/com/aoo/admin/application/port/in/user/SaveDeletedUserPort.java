package com.aoo.admin.application.port.in.user;

import com.aoo.admin.domain.user.DeletedUser;

public interface SaveDeletedUserPort {
    Long saveDeletedUser(DeletedUser deletedUser);
}
