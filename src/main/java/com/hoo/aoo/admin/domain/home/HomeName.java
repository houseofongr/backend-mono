package com.hoo.aoo.admin.domain.home;

import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.user.User;
import lombok.Getter;

@Getter
public class HomeName {
    private final String name;

    public HomeName(House house, User user) {
        this.name = user.getUserName().getNickName() + "의 " + house.getId().getTitle();
    }
}
