package com.hoo.aar.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserF {
    NOT_REGISTERED(null, true, true);

    private final SnsAccount snsAccount;
    private final Boolean recordAgreement;
    private final Boolean personalInformationAgreement;

    public User get() {
        return new User(snsAccount, recordAgreement, personalInformationAgreement);
    }
}
