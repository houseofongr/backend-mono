package com.hoo.aoo.aar.domain.user;

import lombok.Getter;

@Getter
public class UserInfo {
    private final String realName;
    private final String nickname;
    private final String email;

    public UserInfo(String realName, String nickname, String email) {
        this.nickname = nickname;
        this.realName = realName;
        this.email = email;
    }
}
