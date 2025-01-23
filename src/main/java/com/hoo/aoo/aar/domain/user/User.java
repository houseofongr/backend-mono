package com.hoo.aoo.aar.domain.user;

import com.hoo.aoo.aar.domain.Name;
import com.hoo.aoo.aar.domain.account.SnsAccount;
import com.hoo.aoo.aar.domain.account.SnsAccountId;
import com.hoo.aoo.aar.domain.exception.InvalidPhoneNumberException;
import lombok.Getter;

import java.util.List;

@Getter
public class User {

    private final UserId phoneNumber;
    private final Agreement agreement;
    private final List<SnsAccountId> snsAccounts;
    private final Name name;

    public User(Agreement agreement, UserId phoneNumber, List<SnsAccountId> snsAccounts, Name name) {
        this.agreement = agreement;
        this.phoneNumber = phoneNumber;
        this.snsAccounts = snsAccounts;
        this.name = name;
    }

    public static User registerWithDefaultPhoneNumber(SnsAccount snsAccount, Boolean termsOfUseAgreement, Boolean personalInformationAgreement) throws InvalidPhoneNumberException {

        return new User(
                new Agreement(termsOfUseAgreement,personalInformationAgreement),
                new UserId(null),
                List.of(snsAccount.getSnsAccountId()),
                snsAccount.getName()
        );
    }
}
