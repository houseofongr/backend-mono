package com.hoo.aoo.admin.application.port.out.home;

import com.hoo.aoo.admin.application.port.in.home.QueryUserHomesResult;

public interface FindUserHomesPort {
    QueryUserHomesResult findUserHomes(Long id);
}
