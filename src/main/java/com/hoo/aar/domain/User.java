package com.hoo.aar.domain;

import lombok.Getter;

@Getter
public class User {
    private Long id;
    private String name;
    private String nickname;
    private String phoneNumber;
    private final Boolean recordAgreement;
    private final Boolean personalInformationAgreement;
    private SnsAccount snsAccount;

    public User(SnsAccount snsAccount, Boolean recordAgreement, Boolean personalInformationAgreement) {
        this.name = snsAccount.getName();
        this.nickname = snsAccount.getNickname();
        this.snsAccount = snsAccount;
        this.phoneNumber = "NOT_SET";
        this.recordAgreement = recordAgreement;
        this.personalInformationAgreement = personalInformationAgreement;
    }

}
