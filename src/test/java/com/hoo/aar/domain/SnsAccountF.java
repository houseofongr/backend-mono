package com.hoo.aar.domain;

import com.hoo.aar.common.enums.SnsDomain;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SnsAccountF {

    KAKAO_NOT_REGISTERED(1L, "남상엽", "leaf", "test@example.com", "SNS_ID", SnsDomain.KAKAO, null),
    KAKAO_NOT_REGISTERED_WITH_NO_ID(null, "남상엽", "leaf", "test@example.com", "SNS_ID", SnsDomain.KAKAO, null);

    private final Long id;
    private final String name;
    private final String nickname;
    private final String email;
    private final String snsId;
    private final SnsDomain snsDomain;
    private final User user;

    public SnsAccount get() {
        return new SnsAccount(id, name, nickname, email, snsId, snsDomain, user);
    }
}
