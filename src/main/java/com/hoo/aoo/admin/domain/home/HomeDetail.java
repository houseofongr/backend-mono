package com.hoo.aoo.admin.domain.home;

import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.user.User;
import lombok.Getter;

@Getter
public class HomeDetail {
    private final String name;

    public HomeDetail(House house, User user) {
        this.name = user.getUserName().getNickName() + "의 " + house.getHouseDetail().getTitle();
    }
}
