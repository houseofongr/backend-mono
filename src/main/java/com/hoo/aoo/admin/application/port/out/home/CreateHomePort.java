package com.hoo.aoo.admin.application.port.out.home;

import com.hoo.aoo.admin.domain.home.Home;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.user.User;

public interface CreateHomePort {
    Home createHome(House house, User user);
}
