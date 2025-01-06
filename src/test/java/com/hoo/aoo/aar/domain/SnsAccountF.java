package com.hoo.aoo.aar.domain;

import com.hoo.aoo.common.enums.SnsDomain;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SnsAccountF {

    KAKAO_NOT_REGISTERED(1L, "남상엽", "leaf", "leaf@example.com", "SNS_ID", SnsDomain.KAKAO, null),
    KAKAO_NOT_REGISTERED_WITH_NO_ID(null, "남상엽", "leaf", "leaf@example.com", "SNS_ID", SnsDomain.KAKAO, null),
    KAKAO_NOT_REGISTERED_2(2L, "남엽돌", "spearoad", "spearoad@example.com", "SNS_ID_2", SnsDomain.KAKAO, null);

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
