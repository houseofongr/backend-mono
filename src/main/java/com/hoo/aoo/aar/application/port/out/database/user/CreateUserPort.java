package com.hoo.aoo.aar.application.port.out.database.user;

import com.hoo.aoo.aar.domain.user.snsaccount.SnsAccount;
import com.hoo.aoo.aar.domain.user.User;

public interface CreateUserPort {
    User createUser(SnsAccount snsAccount, Boolean termsOfUseAgreement, Boolean personalInformationAgreement);
}
