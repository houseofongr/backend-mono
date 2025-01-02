package com.hoo.aar.domain;

import lombok.Getter;

@Getter
public class User {
    private Long id;
    private String name;
    private String nickname;
    private final Boolean recordAgreement;
    private final Boolean personalInformationAgreement;
    private SnsAccount snsAccount;

    public User(SnsAccount snsAccount, String nickname, Boolean recordAgreement, Boolean personalInformationAgreement) {
        this.snsAccount = snsAccount;
        this.nickname = nickname;
        this.recordAgreement = recordAgreement;
        this.personalInformationAgreement = personalInformationAgreement;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
