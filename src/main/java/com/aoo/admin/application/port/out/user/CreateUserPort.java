package com.aoo.admin.application.port.out.user;

import com.aoo.admin.domain.user.User;
import com.aoo.admin.domain.user.snsaccount.SnsAccount;

public interface CreateUserPort {
    User createUser(SnsAccount snsAccount, Boolean termsOfUseAgreement, Boolean personalInformationAgreement);
}
