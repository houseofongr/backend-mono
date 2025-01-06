package com.hoo.aoo.aar.domain;

import com.hoo.aoo.aar.domain.account.SnsAccount;
import com.hoo.aoo.aar.domain.account.SnsAccountId;
import com.hoo.aoo.aar.domain.exception.InvalidPhoneNumberException;
import com.hoo.aoo.aar.domain.user.Agreement;
import com.hoo.aoo.aar.domain.user.PhoneNumber;
import com.hoo.aoo.aar.domain.user.User;

import java.util.List;

public class DomainFixtureRepository {
    public static User getRegisteredUser(SnsAccount snsAccount) throws InvalidPhoneNumberException {

        return new User(
                new Agreement(true, true),
                new PhoneNumber("010-0000-0000"),
                List.of(new SnsAccountId(snsAccount.getSnsAccountId().getSnsDomain(), snsAccount.getSnsAccountId().getSnsId())),
                snsAccount.getName()
        );
    }

    public static SnsAccount getRegisteredSnsAccount() throws InvalidPhoneNumberException {
        SnsAccount snsAccount = SnsAccountF.REGISTERED_KAKAO.get();

        return new SnsAccount(snsAccount.getEmail(), snsAccount.getSnsAccountId(), snsAccount.getName(), new PhoneNumber("010-0000-0000"), snsAccount.getDateInfo());
    }
}
