package com.hoo.aoo.aar.domain.user;

import lombok.Getter;

@Getter
public class Agreement {
    private final Boolean recordAgreement;
    private final Boolean personalInformationAgreement;

    public Agreement(Boolean personalInformationAgreement, Boolean recordAgreement) {
        this.personalInformationAgreement = personalInformationAgreement;
        this.recordAgreement = recordAgreement;
    }
}
