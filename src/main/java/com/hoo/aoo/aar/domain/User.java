package com.hoo.aoo.aar.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class User {
    private Long id;
    private String name;
    private String nickname;
    private String phoneNumber;
    private final Boolean recordAgreement;
    private final Boolean personalInformationAgreement;
    private final List<SnsAccount> snsAccounts;

    public User(Long id, String name, String nickname, String phoneNumber, Boolean recordAgreement, Boolean personalInformationAgreement, List<SnsAccount> snsAccounts) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.personalInformationAgreement = personalInformationAgreement;
        this.phoneNumber = phoneNumber;
        this.recordAgreement = recordAgreement;
        this.snsAccounts = snsAccounts;
    }

    public static User regist(SnsAccount snsAccount, Boolean recordAgreement, Boolean personalInformationAgreement) {
        User newUser = new User(null, snsAccount.getName(), snsAccount.getNickname(), "NOT_SET", recordAgreement, personalInformationAgreement, List.of(snsAccount));
        snsAccount.setUser(newUser);

        return newUser;
    }
}
