package com.hoo.aoo.aar.domain.user;

import lombok.Getter;

@Getter
public class Agreement {
    private final Boolean termsOfUseAgreement;
    private final Boolean personalInformationAgreement;

    public Agreement(Boolean personalInformationAgreement, Boolean termsOfUseAgreement) {
        this.personalInformationAgreement = personalInformationAgreement;
        this.termsOfUseAgreement = termsOfUseAgreement;
    }
}
