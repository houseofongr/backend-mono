package com.aoo.admin.application.port.out.home;

import com.aoo.admin.domain.home.Home;
import com.aoo.admin.domain.house.House;
import com.aoo.admin.domain.user.User;

public interface CreateHomePort {
    Home createHome(House house, User user);
}
