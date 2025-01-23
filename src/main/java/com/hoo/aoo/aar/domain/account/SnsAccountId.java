package com.hoo.aoo.aar.domain.account;

import lombok.Getter;

@Getter
public class SnsAccountId {

    private final String snsId;
    private final SnsDomain snsDomain;

    public SnsAccountId(SnsDomain snsDomain, String snsId) {
        this.snsDomain = snsDomain;
        this.snsId = snsId;
    }

}
