package com.hoo.aoo.admin.domain.home;

import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.user.User;
import lombok.Getter;

@Getter
public class Home {
    private final HomeId homeId;
    private final HomeDetail detail;

    private Home(HomeId homeId, HomeDetail detail) {
        this.homeId = homeId;
        this.detail = detail;
    }

    public static Home create(Long id, House house, User user) {

        HomeId homeId = new HomeId(id);
        HomeDetail detail = new HomeDetail(house, user);

        return new Home(homeId, detail);
    }
}
