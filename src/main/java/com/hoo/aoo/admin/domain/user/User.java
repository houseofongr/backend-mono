package com.hoo.aoo.admin.domain.user;

import lombok.Getter;

@Getter
public class User {
    private final UserName userName;

    private User(String nickName, String realName) {
        this.userName = new UserName(nickName, realName);
    }

    public static User load(String nickName, String realName) {
        return new User(nickName, realName);
    }
}
