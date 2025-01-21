package com.hoo.aoo.admin.domain.home;

import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.user.User;
import lombok.Getter;

@Getter
public class Home {
    private final HomeName homeName;

    private Home(HomeName homeName) {
        this.homeName = homeName;
    }

    public static Home create(House house, User user) {

        HomeName homeName = new HomeName(house, user);

        return new Home(homeName);
    }
}
