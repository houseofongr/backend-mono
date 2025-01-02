package com.hoo.aar.adapter.out.persistence.entity;

import com.hoo.aar.common.enums.SnsDomain;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SnsAccountJpaEntityF {

    KAKAO(1L, "leaf", "test@example.com", "SNS_ID", SnsDomain.KAKAO),
    KAKAO_NO_ID(null, "leaf", "test@example.com", "SNS_ID", SnsDomain.KAKAO);

    private final Long id;
    private final String name;
    private final String email;
    private final String snsId;
    private final SnsDomain snsDomain;

    public SnsAccountJpaEntity get() {
        return new SnsAccountJpaEntity(id, name, email, snsId, snsDomain, null);
    }
}
