package com.hoo.aoo.admin.application.port.out.home;

import com.hoo.aoo.admin.domain.home.Home;

public interface UpdateHomePort {
    void updateHomeName(Home home);
    void updateMainHome(Long userId, Long homeId);
}
