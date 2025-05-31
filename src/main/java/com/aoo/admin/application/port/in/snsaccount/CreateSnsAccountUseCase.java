package com.aoo.admin.application.port.in.snsaccount;

import com.aoo.admin.domain.user.snsaccount.SnsAccount;
import com.aoo.admin.domain.user.snsaccount.SnsDomain;

public interface CreateSnsAccountUseCase {
    SnsAccount createSnsAccount(SnsDomain domain, String snsId, String realName, String nickname, String email);
}
