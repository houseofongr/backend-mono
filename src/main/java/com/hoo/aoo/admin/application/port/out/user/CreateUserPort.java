package com.hoo.aoo.admin.application.port.out.user;

import com.hoo.aoo.admin.domain.user.User;
import com.hoo.aoo.admin.domain.user.snsaccount.SnsAccount;

public interface CreateUserPort {
    User createUser(SnsAccount snsAccount, Boolean termsOfUseAgreement, Boolean personalInformationAgreement);
}
