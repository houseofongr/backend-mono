package com.hoo.aar.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserF {
    REGISTERED_WITH_NO_ID(SnsAccountF.KAKAO_NOT_REGISTERED.get(), true, true);

    private final SnsAccount snsAccount;
    private final Boolean recordAgreement;
    private final Boolean personalInformationAgreement;

    public User get() {
        return User.regist(snsAccount, recordAgreement, personalInformationAgreement);
    }
}
