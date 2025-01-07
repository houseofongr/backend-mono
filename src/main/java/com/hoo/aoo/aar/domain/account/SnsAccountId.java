package com.hoo.aoo.aar.domain.account;

import lombok.Getter;

public class SnsAccountId {

    @Getter
    private final String snsId;

    @Getter
    private final SnsDomain snsDomain;

    public SnsAccountId(SnsDomain snsDomain, String snsId) {
        this.snsDomain = snsDomain;
        this.snsId = snsId;
    }

}
