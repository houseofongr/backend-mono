package com.aoo.admin.application.port.out.user;

import com.aoo.admin.domain.user.BusinessUser;

public interface FindBusinessUserPort {
    BusinessUser findBusinessUser(Long businessUserId);
}
