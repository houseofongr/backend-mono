package com.hoo.aoo.aar.domain;

import lombok.Getter;

@Getter
public class Name {
    private final String realName;
    private final String nickname;

    public Name(String realName, String nickname) {
        this.realName = realName;
        this.nickname = nickname;
    }
}
