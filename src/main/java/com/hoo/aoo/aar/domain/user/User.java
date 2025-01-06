package com.hoo.aoo.aar.domain.user;

import com.hoo.aoo.aar.domain.Name;
import com.hoo.aoo.aar.domain.account.SnsAccount;
import com.hoo.aoo.aar.domain.account.SnsAccountId;
import com.hoo.aoo.aar.domain.exception.InvalidPhoneNumberException;
import lombok.Getter;

import java.util.List;

@Getter
public class User {
    public static final String DEFAULT_PHONE_NUMBER = "010-0000-0000";

    private final PhoneNumber phoneNumber;
    private final Agreement agreement;
    private final List<SnsAccountId> snsAccounts;
    private Name name;

    public User(Agreement agreement, PhoneNumber phoneNumber, List<SnsAccountId> snsAccounts, Name name) {
        this.agreement = agreement;
        this.phoneNumber = phoneNumber;
        this.snsAccounts = snsAccounts;
        this.name = name;
    }

    public static User registerWithDefaultPhoneNumber(SnsAccount snsAccount, Boolean recordAgreement, Boolean personalInformationAgreement) throws InvalidPhoneNumberException {

        return new User(
                new Agreement(recordAgreement,personalInformationAgreement),
                new PhoneNumber(DEFAULT_PHONE_NUMBER),
                List.of(snsAccount.getSnsAccountId()),
                snsAccount.getName()
        );
    }
}
