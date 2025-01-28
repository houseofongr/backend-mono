package com.hoo.aoo.aar.application.port.out.user;

import com.hoo.aoo.aar.domain.user.User;
import com.hoo.aoo.aar.domain.user.snsaccount.SnsAccount;

public interface CreateUserPort {
    User createUser(SnsAccount snsAccount, Boolean termsOfUseAgreement, Boolean personalInformationAgreement);
}
