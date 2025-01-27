package com.hoo.aoo.admin.domain.user;

import lombok.Getter;

@Getter
public class User {
    private final UserId userId;
    private final UserName userName;

    private User(UserId userId, String nickName, String realName) {
        this.userId = userId;
        this.userName = new UserName(nickName, realName);
    }

    public static User load(Long userId, String nickName, String realName) {
        return new User(new UserId(userId), nickName, realName);
    }
}
