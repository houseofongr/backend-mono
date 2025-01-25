package com.hoo.aoo.aar.domain.user;

import lombok.Getter;

@Getter
public class UserInfo {
    private final String realName;
    private final String nickname;

    public UserInfo(String realName, String nickname) {
        this.nickname = nickname;
        this.realName = realName;
    }
}
