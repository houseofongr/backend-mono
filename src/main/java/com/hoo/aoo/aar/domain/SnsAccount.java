package com.hoo.aoo.aar.domain;

import com.hoo.aoo.common.enums.SnsDomain;
import lombok.Getter;
import lombok.Setter;

@Getter
public class SnsAccount {
    private final Long id;
    private final String name;
    private final String nickname;
    private final String email;
    private final String snsId;
    private final SnsDomain snsDomain;

    @Setter
    private User user;

    public SnsAccount(Long id, String name, String nickname, String email, String snsId, SnsDomain snsDomain, User user) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.snsId = snsId;
        this.snsDomain = snsDomain;
        this.user = user;
    }

    public static SnsAccount regist(String nickname, String email, String snsId, SnsDomain snsDomain) {
        return new SnsAccount(null, nickname, nickname, email, snsId, snsDomain, null);
    }
}
